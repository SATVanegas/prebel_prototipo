package com.prebel.prototipo.webapp.services.utils;

import com.prebel.prototipo.webapp.models.role_module.User;
import com.prebel.prototipo.webapp.services.role_module_services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final UserService userService;

    public EmailService(JavaMailSender mailSender, UserService userService) {
        this.mailSender = mailSender;
        this.userService = userService;
    }

    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    public ResponseEntity<String> sendResetCode(String email) {
        Optional<User> optionalUser = userService.findByEmail(email);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }

        User user = optionalUser.get();
        String resetCode = generateResetCode();
        userService.updateResetCode(user, resetCode);

        sendEmail(email, "Código de recuperación", "Tu código de recuperación es: " + resetCode);
        return ResponseEntity.ok("Código de recuperación enviado a " + email);
    }

    public ResponseEntity<String> changePassword(String email, String code, String newPassword) {
        Optional<User> optionalUser = userService.findByEmail(email);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }

        User user = optionalUser.get();
        if (!code.equals(user.getResetCode())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Código de recuperación incorrecto");
        }

        userService.updatePassword(user, newPassword);
        return ResponseEntity.ok("Contraseña cambiada correctamente");
    }

    private String generateResetCode() {
        return String.valueOf(new Random().nextInt(900000) + 100000);
    }
}


