package com.prebel.prototipo.webapp.repositories;

import com.prebel.prototipo.webapp.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByEmail(String email);

}
