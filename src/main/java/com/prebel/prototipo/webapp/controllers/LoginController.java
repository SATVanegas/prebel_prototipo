package com.prebel.prototipo.webapp.controllers;

import com.prebel.prototipo.webapp.models.permissions.RoleModuleDTO;
import com.prebel.prototipo.webapp.models.permissions.User;
import com.prebel.prototipo.webapp.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

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

        List<RoleModuleDTO> modulesWithPermissions = user.getRole().getRoleModules().stream()
                .map(rm -> new RoleModuleDTO(rm.getModule().getName(), rm.getPermissions()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(modulesWithPermissions);
    }
}

