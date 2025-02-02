package com.prebel.prototipo.webapp.controllers;

import com.prebel.prototipo.webapp.models.User;
import com.prebel.prototipo.webapp.dtos.RoleModuleDTO;
import com.prebel.prototipo.webapp.dtos.RoleModuleRequestDTO;
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

@RestController
@RequestMapping("/api/roles")
public class RolController {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final ModuleRepository moduleRepository;
    private final RoleModuleRepository roleModuleRepository;

    public RolController(
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
        role.setRoleEnum(request.getRoleEnum());
        roleRepository.save(role);

        // Asignar módulos y permisos
        request.getModules().forEach(rmRequest -> {
            Module module = moduleRepository.findById(rmRequest.getModuleId())
                    .orElseThrow(() -> new RuntimeException("Módulo no encontrado"));

            RoleModule roleModule = new RoleModule();
            roleModule.setRole(role);
            roleModule.setModule(module);
            roleModule.setPermissions(rmRequest.getPermissions());
            roleModuleRepository.save(roleModule);
        });

        return ResponseEntity.status(HttpStatus.CREATED).body(role);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRole(@PathVariable long id, @RequestBody RoleRequestDTO roleDetails) {
        Optional<Role> roleOptional = roleRepository.findById(id);

        if (roleOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Rol no encontrado");
        }

        Role role = roleOptional.get();
        role.setRoleEnum(roleDetails.getRoleEnum());

        List<RoleModule> existingRoleModules = role.getRoleModules();

        for (RoleModuleRequestDTO rmRequest : roleDetails.getModules()) {
            Optional<RoleModule> existingRoleModuleOpt = existingRoleModules.stream()
                    .filter(rm -> rm.getModule().getId().equals(rmRequest.getModuleId()))
                    .findFirst();

            if (existingRoleModuleOpt.isPresent()) {
                RoleModule existingRoleModule = existingRoleModuleOpt.get();
                existingRoleModule.setPermissions(rmRequest.getPermissions());
            } else {
                Module module = moduleRepository.findById(rmRequest.getModuleId())
                        .orElseThrow(() -> new RuntimeException("Módulo no encontrado"));

                RoleModule newRoleModule = new RoleModule();
                newRoleModule.setRole(role);
                newRoleModule.setModule(module);
                newRoleModule.setPermissions(rmRequest.getPermissions());
                existingRoleModules.add(newRoleModule);
            }
        }

        List<Long> moduleIdsInRequest = roleDetails.getModules().stream()
                .map(RoleModuleRequestDTO::getModuleId)
                .toList();

        existingRoleModules.removeIf(rm -> !moduleIdsInRequest.contains(rm.getModule().getId()));
        
        roleRepository.save(role);

        return ResponseEntity.ok("Rol actualizado correctamente");
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

        List<RoleModule> roleModule = role.getRoleModules();

        List<RoleModuleDTO> modulesWithPermissions = roleModule.stream()
                .map(rm -> new RoleModuleDTO(rm.getModule().getName(), rm.getPermissions()))
                .toList();
        return ResponseEntity.ok(modulesWithPermissions);
    }

    @GetMapping
    public ResponseEntity<List<Roles>> getAllRoles() {
        List<Roles> roleEnums = ((List<Role>) roleRepository.findAll()).stream().map(Role::getRoleEnum).toList();
        return ResponseEntity.ok(roleEnums);
    }
}
