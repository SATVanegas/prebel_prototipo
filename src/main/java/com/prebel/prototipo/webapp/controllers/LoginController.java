package com.prebel.prototipo.webapp.controllers;

import com.prebel.prototipo.webapp.models.User;
import com.prebel.prototipo.webapp.repositories.UserRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class LoginController {

    private UserRepository userRepository;

    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/login/{email}/{password}")
    public User login(@PathVariable String email, @PathVariable String password) {
       var user = userRepository.findByEmail(email);
         if (user != null && user.getPassword().equals(password)) {
              return user;
         } else {
              return null;
         }
    }

}
