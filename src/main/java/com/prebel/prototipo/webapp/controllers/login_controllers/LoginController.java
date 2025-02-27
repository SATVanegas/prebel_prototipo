package com.prebel.prototipo.webapp.controllers.login_controllers;

import com.prebel.prototipo.webapp.services.login_services.LoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) { this.loginService = loginService;}

    @GetMapping("/login/{email}/{password}")
    public ResponseEntity<?> login(@PathVariable String email, @PathVariable String password) {
        return loginService.authenticateUser(email, password);
    }
}

