package com.prebel.prototipo.webapp.controllers;

import com.prebel.prototipo.webapp.models.User;
import com.prebel.prototipo.webapp.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    private UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        // Verificar si el email ya está registrado
        if (userRepository.findByEmail(user.getEmail()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("El email ya está registrado");
        }

        // Guardar el usuario en la base de datos
        User savedUser = userRepository.save(user);

        // Devolver una respuesta HTTP 201 (CREATED) con el usuario guardado
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }



}
