package com.prebel.prototipo.webapp.services.login_services;

import com.prebel.prototipo.webapp.models.role_module.User;
import com.prebel.prototipo.webapp.services.role_module_services.UserService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
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

    // Método existente: Envío de email en texto plano
    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    // Nuevo método: Envío de email en formato HTML
    public void sendHtmlEmail(String to, String subject, String templatePath, Map<String, String> placeholders) throws MessagingException, IOException {
        String htmlContent = loadEmailTemplate(templatePath);

        // Reemplazar placeholders en la plantilla
        for (Map.Entry<String, String> entry : placeholders.entrySet()) {
            htmlContent = htmlContent.replace("{{" + entry.getKey() + "}}", entry.getValue());
        }

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true);

        mailSender.send(message);
    }

    // Modificación para usar HTML en la recuperación de código
    public ResponseEntity<String> sendResetCode(String email) {
        Optional<User> optionalUser = userService.findByEmail(email);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }

        User user = optionalUser.get();
        String resetCode = generateResetCode();
        userService.updateResetCode(user, resetCode);

        try {
            Map<String, String> placeholders = new HashMap<>();
            placeholders.put("resetCode", resetCode);

            sendHtmlEmail(email, "Código de recuperación", "templates/reset-code.html", placeholders);
            return ResponseEntity.ok("Código de recuperación enviado a " + email);
        } catch (MessagingException | IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al enviar el correo");
        }
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

    private String loadEmailTemplate(String path) throws IOException {
        ClassPathResource resource = new ClassPathResource(path);
        return Files.readString(resource.getFile().toPath());
    }
}


