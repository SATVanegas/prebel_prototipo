package com.prebel.prototipo.webapp.repositories;

import com.prebel.prototipo.webapp.models.role_module.Module;
import com.prebel.prototipo.webapp.models.role_module.Role;
import com.prebel.prototipo.webapp.models.role_module.RoleModule;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RoleModuleRepository extends CrudRepository<RoleModule, Long> {
    List<RoleModule> findByRole(Role role);

    Optional<RoleModule> findByRoleAndModule(Role role, Module module);
}
