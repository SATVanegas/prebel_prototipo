package com.prebel.prototipo.webapp.controllers;

import com.prebel.prototipo.webapp.models.dtos.LoginResponseDTO;
import com.prebel.prototipo.webapp.models.role_module.Roles;
import com.prebel.prototipo.webapp.models.User;
import com.prebel.prototipo.webapp.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api")
public class LoginController {
    private final UserRepository userRepository;

    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/login/{email}/{password}")
    public ResponseEntity<?> login(@PathVariable String email, @PathVariable String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        if (!user.getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Contraseña incorrecta");
        }

        Roles roleEnum = user.getRole().getRoleEnum();
        String name = user.getName();

        return ResponseEntity.ok(new LoginResponseDTO(roleEnum, name));
    }
}

