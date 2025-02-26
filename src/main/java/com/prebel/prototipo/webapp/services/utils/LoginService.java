package com.prebel.prototipo.webapp.services.utils;

import com.prebel.prototipo.webapp.dtos.LoginResponseDTO;
import com.prebel.prototipo.webapp.models.role_module.User;
import com.prebel.prototipo.webapp.services.role_module_services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class LoginService {

    private final UserService userService;

    public LoginService(UserService userService) {
        this.userService = userService;
    }

    public ResponseEntity<?> authenticateUser(String email, String password) {
        User user = userService.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        if (!user.getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Contraseña incorrecta");
        }

        return ResponseEntity.ok(new LoginResponseDTO(user.getRole().getRoleName(), user.getName()));
    }
}
