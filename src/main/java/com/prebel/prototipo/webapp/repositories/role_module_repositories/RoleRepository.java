package com.prebel.prototipo.webapp.repositories.role_module_repositories;

import com.prebel.prototipo.webapp.models.role_module.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface RoleRepository extends CrudRepository<Role, Long> {
    Optional<Role> findByRoleName(String roleName);
}

