package com.prebel.prototipo.webapp.services.role_module_services;

import com.prebel.prototipo.webapp.models.role_module.Role;
import com.prebel.prototipo.webapp.repositories.role_module_repositories.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Optional<Role> getRoleById(Long id) {return roleRepository.findById(id);}
}
