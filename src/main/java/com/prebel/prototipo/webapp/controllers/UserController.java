package com.prebel.prototipo.webapp.controllers;

import com.prebel.prototipo.webapp.models.permissions.Role;
import com.prebel.prototipo.webapp.models.permissions.User;
import com.prebel.prototipo.webapp.models.permissions.UserRegistrationDTO;
import com.prebel.prototipo.webapp.repositories.RoleRepository;
import com.prebel.prototipo.webapp.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserController(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegistrationDTO request) {
        // Validar si el email ya existe
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("El email ya está registrado");
        }

        // Validar si el rol existe
        if (request.getRoleEnum() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("El rol es obligatorio");
        }

        Optional<Role> roleOptional = roleRepository.findByRoleEnum(request.getRoleEnum());
        if (roleOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Rol no encontrado");
        }

        // Crear y guardar el usuario
        User user = new User();
        user.setPassword(request.getPassword());
        user.setName(request.getName());
        user.setNumber(request.getNumber());
        user.setEmail(request.getEmail());
        user.setRole(roleOptional.get());

        User savedUser = userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body("Usuario creado exitosamente");
    }
}
