package by.hackaton.bookcrossing.controller;

import by.hackaton.bookcrossing.dto.request.EmailMessageRequest;
import by.hackaton.bookcrossing.service.EmailService;
import by.hackaton.bookcrossing.util.AuthUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    private EmailService emailService;

    public FeedbackController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> sendEmail(@RequestBody EmailMessageRequest request, Authentication auth){
        emailService.sendFeedbackMessage(AuthUtils.getEmailFromAuth(auth), request.subject, request.body);
        return ok().build();
    }
}
