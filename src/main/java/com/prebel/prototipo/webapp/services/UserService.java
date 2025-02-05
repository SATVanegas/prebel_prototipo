package com.prebel.prototipo.webapp.services;

import com.prebel.prototipo.webapp.models.User;
import com.prebel.prototipo.webapp.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final EmailService emailService;

    public UserService(UserRepository userRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    // Método para enviar un código de recuperación de contraseña al correo electrónico
    public void sendPasswordResetCode(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("Usuario no encontrado");
        }

        User user = optionalUser.get();

        // Generar código aleatorio
        String resetCode = String.valueOf((int) (Math.random() * 9000) + 1000);
        user.setResetCode(resetCode);
        userRepository.save(user);

        // Enviar código al correo
        emailService.sendEmail(email, "Código de recuperación", "Tu código es: " + resetCode);
    }
}
