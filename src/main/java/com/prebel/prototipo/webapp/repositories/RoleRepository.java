package com.prebel.prototipo.webapp.repositories;

import com.prebel.prototipo.webapp.models.permissions.Role;
import com.prebel.prototipo.webapp.models.permissions.Roles;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Optional<Role> findByRoleEnum(Roles roleEnum);
}

