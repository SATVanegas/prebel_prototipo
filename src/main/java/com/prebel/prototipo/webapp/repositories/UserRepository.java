package com.prebel.prototipo.webapp.repositories;

import com.prebel.prototipo.webapp.models.permissions.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmail(String email);
}