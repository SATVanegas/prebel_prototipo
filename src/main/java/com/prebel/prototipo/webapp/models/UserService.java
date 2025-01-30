package com.prebel.prototipo.webapp.models;

import com.prebel.prototipo.webapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    // Método para enviar un código de recuperación de contraseña al correo electrónico
    public void sendPasswordResetCode(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("Usuario no encontrado");
        }

        // Generar código aleatorio
        String resetCode = String.valueOf((int) (Math.random() * 9000) + 1000);
        user.setResetCode(resetCode);
        userRepository.save(user);

        // Enviar código al correo
        emailService.sendEmail(email, "Código de recuperación", "Tu código es: " + resetCode);
    }
}
