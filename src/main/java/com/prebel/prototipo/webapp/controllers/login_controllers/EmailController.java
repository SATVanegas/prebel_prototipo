package com.prebel.prototipo.webapp.controllers.login_controllers;

import com.prebel.prototipo.webapp.services.EmailService;
import com.prebel.prototipo.webapp.models.role_module.User;
import com.prebel.prototipo.webapp.services.UserService;
import com.prebel.prototipo.webapp.repositories.role_module_repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    private final EmailService emailService;
    private final UserService userService;
    private final UserRepository userRepository;

    public EmailController(EmailService emailService, UserService userService, UserRepository userRepository) {
        this.emailService = emailService;
        this.userService = userService;
        this.userRepository = userRepository;
    }
    // Endpoint PRUEBA para enviar un correo electrónico a una dirección específica con un asunto y cuerpo
    @GetMapping("/send/{to}/{subject}/{text}")
    public String sendEmail(
            @PathVariable String to,
            @PathVariable String subject,
            @PathVariable String text) {

        emailService.sendEmail(to, subject, text);
        return "Correo enviado a " + to + " con asunto: " + subject;
    }

    // Endpoint para enviar un código de recuperación de contraseña al correo de un usuario
    @GetMapping("/send/resetCode/{email}")
    public ResponseEntity<String> sendResetCode(@PathVariable String email) {
        try {
            userService.sendPasswordResetCode(email);
            return ResponseEntity.ok("Código de recuperación enviado a " + email);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Endpoint para cambiar la contraseña de un usuario mediante un código de recuperación
    @GetMapping("/changePassword/{email}/{code}/{newPassword}")
    public ResponseEntity<String> changePassword(
            @PathVariable String email,
            @PathVariable String code,
            @PathVariable String newPassword) {

        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }

        User user = optionalUser.get();

        // Validar código de recuperación
        if (!code.equals(user.getResetCode())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Código de recuperación incorrecto");
        }

        // Cambiar la contraseña y borrar el código de recuperación
        user.setPassword(newPassword);
        user.setResetCode(null);
        userRepository.save(user);

        return ResponseEntity.ok("Contraseña cambiada correctamente");
    }
}
