package by.hackaton.bookcrossing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;

@Service
public class EmailService {

    private static final String TEMPLATE_URL = "classpath:templates/ConfirmEmail.html";
    private String template;
    private ResourceLoader resourceLoader;

    @Autowired
    private JavaMailSender emailSender;

    public EmailService(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public void sendFeedbackMessage(String from, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("a.maiseyenak@gmail.com");
        message.setTo("a.maiseyenak@gmail.com");
        message.setSubject(subject);
        message.setText("Message from: " + from + "\n\n" + text);
        emailSender.send(message);
    }

    public void sendMessage(String to, String subject, String link) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("a.maiseyenak@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(getTemplate().replace("{{link}}", link));
        emailSender.send(message);
    }

    private String getTemplate() {
        try {
            if (template == null) {
                template = FileCopyUtils.copyToString(new InputStreamReader(resourceLoader.getResource(TEMPLATE_URL).getInputStream()));
            }
            return template;
        } catch (IOException ex) {
            ex.getMessage();
        }
        return "";
    }
}
