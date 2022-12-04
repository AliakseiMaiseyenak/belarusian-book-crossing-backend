package by.hackaton.bookcrossing.controller;

import by.hackaton.bookcrossing.dto.request.EmailRequest;
import by.hackaton.bookcrossing.service.EmailService;
import by.hackaton.bookcrossing.util.AuthUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    private EmailService emailService;

    public FeedbackController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping
    public ResponseEntity<Void> sendEmail(@RequestBody EmailRequest request, Authentication auth){
        emailService.sendFeedbackMessage(AuthUtils.getEmailFromAuth(auth), request.subject, request.body);
        return ok().build();
    }
}
