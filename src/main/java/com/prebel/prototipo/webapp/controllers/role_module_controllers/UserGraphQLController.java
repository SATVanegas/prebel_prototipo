package com.prebel.prototipo.webapp.controllers.role_module_controllers;

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
    public User createUser(@Argument User input) {
        return userService.save(input);
    }

    @MutationMapping
    public User updateUser(@Argument User input) {
        return userService.save(input);
    }

    @MutationMapping
    public boolean deleteUser(@Argument Long id) {
        return userService.deleteUserById(id);
    }
}
