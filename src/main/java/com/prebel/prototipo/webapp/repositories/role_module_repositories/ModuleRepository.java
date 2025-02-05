package com.prebel.prototipo.webapp.repositories.role_module_repositories;

import com.prebel.prototipo.webapp.models.role_module.Module;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ModuleRepository extends CrudRepository<Module, Long> {
    Optional<Module> findByName(String moduleName);
}
