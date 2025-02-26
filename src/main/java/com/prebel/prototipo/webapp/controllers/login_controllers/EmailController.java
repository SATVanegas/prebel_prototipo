package com.prebel.prototipo.webapp.controllers.login_controllers;

import com.prebel.prototipo.webapp.services.utils.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) { this.emailService = emailService;}

    @GetMapping("/send/{to}/{subject}/{text}")
    public ResponseEntity<String> sendEmail(
            @PathVariable String to,
            @PathVariable String subject,
            @PathVariable String text) {
        emailService.sendEmail(to, subject, text);
        return ResponseEntity.ok("Correo enviado a " + to + " con asunto: " + subject);
    }

    @GetMapping("/send/resetCode/{email}")
    public ResponseEntity<String> sendResetCode(@PathVariable String email) {
        return emailService.sendResetCode(email);
    }

    @GetMapping("/changePassword/{email}/{code}/{newPassword}")
    public ResponseEntity<String> changePassword(
            @PathVariable String email,
            @PathVariable String code,
            @PathVariable String newPassword) {
        return emailService.changePassword(email, code, newPassword);
    }
}
