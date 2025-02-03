package com.prebel.prototipo.webapp.controllers;

import com.prebel.prototipo.webapp.dtos.UserRoleResponseDTO;
import com.prebel.prototipo.webapp.models.User;
import com.prebel.prototipo.webapp.dtos.RoleModuleDTO;
import com.prebel.prototipo.webapp.dtos.RoleRequestDTO;
import com.prebel.prototipo.webapp.models.role_module.*;
import com.prebel.prototipo.webapp.models.role_module.Module;
import com.prebel.prototipo.webapp.repositories.ModuleRepository;
import com.prebel.prototipo.webapp.repositories.RoleModuleRepository;
import com.prebel.prototipo.webapp.repositories.RoleRepository;
import com.prebel.prototipo.webapp.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final ModuleRepository moduleRepository;
    private final RoleModuleRepository roleModuleRepository;

    public RoleController(
            RoleRepository roleRepository,
            UserRepository userRepository,
            ModuleRepository moduleRepository,
            RoleModuleRepository roleModuleRepository
    ) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.moduleRepository = moduleRepository;
        this.roleModuleRepository = roleModuleRepository;
    }

    @PostMapping
    public ResponseEntity<Role> createRole(@RequestBody RoleRequestDTO request) {
        Role role = new Role();
        role.setRoleName(request.getRoleName());
        if (request.getDescription() != null) {
            role.setDescription(request.getDescription());
        }
        roleRepository.save(role);

        // Asignar módulos y permisos
        request.getModules().forEach(rmRequest -> {
            Module module = moduleRepository.findByName(rmRequest.getModuleName())
                    .orElseThrow(() -> new RuntimeException("Módulo no encontrado"));

            RoleModule roleModule = new RoleModule();
            roleModule.setRole(role);
            roleModule.setModule(module);
            roleModule.setPermissions(rmRequest.getPermissions());
            roleModuleRepository.save(roleModule);
        });

        return ResponseEntity.status(HttpStatus.CREATED).body(role);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserPermissions(@PathVariable long userId) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuario no encontrado");
        }

        User user = userOptional.get();

        Role role = user.getRole();

        if (role == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("El usuario no tiene un rol asignado");
        }

        List<RoleModule> roleModules = role.getRoleModules();

        List<RoleModuleDTO> modulesWithPermissions = roleModules.stream()
                .map(rm -> new RoleModuleDTO(rm.getModule().getName(), rm.getPermissions()))
                .toList();

        // Crear respuesta JSON con el usuario y el nombre del rol
        return ResponseEntity.ok(new UserRoleResponseDTO(user.getName(), role.getRoleName(), modulesWithPermissions));
    }

    @GetMapping
    public ResponseEntity<List<String>> getAllRoles() {
        List<String> roleNames = StreamSupport.stream(roleRepository.findAll().spliterator(), false)
                .map(Role::getRoleName)
                .toList();
        return ResponseEntity.ok(roleNames);
    }
}
