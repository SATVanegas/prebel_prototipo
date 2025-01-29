package com.prebel.prototipo.webapp.controllers;

import com.prebel.prototipo.webapp.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class LoginController {

    private UserRepository userRepository;

    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/login/{email}/{password}")
    public ResponseEntity<?> login(@PathVariable String email, @PathVariable String password) {
        // Buscar el usuario por email
        var user = userRepository.findByEmail(email);

        // Si el usuario no existe
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuario no encontrado");
        }

        // Si la contraseña es incorrecta
        if (!user.getPassword().equals(password)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Contraseña incorrecta");
        }

        // Si las credenciales son correctas
        return ResponseEntity.ok(user);
    }

    @GetMapping("/change-password/{email}/{code}/{newPassword}")
    public ResponseEntity<?> changePassword(@PathVariable String email, @PathVariable String code, @PathVariable String newPassword) {
        // Buscar el usuario por email
        var user = userRepository.findByEmail(email);

        // Si el usuario no existe
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuario no encontrado");
        }

        // Si el código de recuperación es incorrecto
        if (!user.getPassword().equals(code)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Código de recuperación incorrecto");
        }

        // Cambiar la contraseña
        user.setPassword(newPassword);
        userRepository.save(user);

        return ResponseEntity.ok("Contraseña cambiada correctamente");
    }
}

