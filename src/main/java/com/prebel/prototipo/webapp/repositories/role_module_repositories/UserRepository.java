package com.prebel.prototipo.webapp.repositories.role_module_repositories;

import com.prebel.prototipo.webapp.models.role_module.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByName(String userName);

    User getUsersById(long id);

    boolean deleteUserById(long id);
}