package com.prebel.prototipo.webapp.repositories;

import com.prebel.prototipo.webapp.models.Role;
import com.prebel.prototipo.webapp.models.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRoleEnum(Roles roleEnum);
}

