package com.prebel.prototipo.webapp.repositories;

import com.prebel.prototipo.webapp.models.Role;
import com.prebel.prototipo.webapp.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByEmail(String email);

    List<User> findByRole(Role role);}
