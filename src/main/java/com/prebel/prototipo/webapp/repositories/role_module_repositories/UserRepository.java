package com.prebel.prototipo.webapp.repositories.role_module_repositories;

import com.prebel.prototipo.webapp.models.role_module.Role;
import com.prebel.prototipo.webapp.models.role_module.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByName(String userName);
    List<User> findByRole(Role role);

    User getUsersById(long id);

    boolean deleteUserById(long id);
}