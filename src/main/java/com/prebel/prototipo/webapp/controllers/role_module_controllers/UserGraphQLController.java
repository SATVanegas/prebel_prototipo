package com.prebel.prototipo.webapp.controllers.role_module_controllers;

import com.prebel.prototipo.webapp.dtos.UserUpdateInput;
import com.prebel.prototipo.webapp.models.role_module.User;
import com.prebel.prototipo.webapp.repositories.role_module_repositories.UserRepository;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
public class UserGraphQLController {


    private final UserRepository userService;

    public UserGraphQLController(UserRepository userService) {
        this.userService = userService;
    }

    @QueryMapping
    public User getUser(@Argument Long id) {
        return userService.getUsersById(id);
    }

    @QueryMapping
    public List<User> getAllUsers() {
        Iterable<User> iterable = userService.findAll();
        return StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }

    @MutationMapping
    public User createUser(@Argument("input") UserUpdateInput input) {
        // Verificar si el usuario ya existe por email
        if (userService.findByEmail(input.getEmail()).isEmpty()) {

            // Crear el usuario con los datos recibidos
            User newUser = new User();
            newUser.setName(input.getName());
            newUser.setEmail(input.getEmail());
            newUser.setNumber(input.getNumber());
            newUser.setPassword(input.getPassword());
            newUser.setResetCode(input.getResetCode());

            return userService.save(newUser);
        }

        throw new RuntimeException("El email ya está registrado.");
    }


    @MutationMapping
    public User updateUser(@Argument("input") UserUpdateInput input) {
        // Buscar el usuario existente
        User existingUser = userService.findById(Long.valueOf(input.getId()))
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Actualizar solo los campos que no son null
        if (input.getName() != null) existingUser.setName(input.getName());
        if (input.getEmail() != null) existingUser.setEmail(input.getEmail());
        if (input.getNumber() != null) existingUser.setNumber(input.getNumber());
        if (input.getPassword() != null) existingUser.setPassword(input.getPassword());
        if (input.getResetCode() != null) existingUser.setResetCode(input.getResetCode());

        return userService.save(existingUser);
    }


    @MutationMapping
    public boolean deleteUser(@Argument Long id) {
        return userService.deleteUserById(id);
    }
}
