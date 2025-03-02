package com.prebel.prototipo.webapp.controllers.role_module_controllers;

import com.prebel.prototipo.webapp.dtos.RoleModuleDTO;
import com.prebel.prototipo.webapp.dtos.UserRoleResponseDTO;
import com.prebel.prototipo.webapp.models.role_module.User;
import com.prebel.prototipo.webapp.dtos.validations.RoleRequestDTO;
import com.prebel.prototipo.webapp.models.role_module.Role;
import com.prebel.prototipo.webapp.models.role_module.RoleModule;
import com.prebel.prototipo.webapp.models.role_module.Module;
import com.prebel.prototipo.webapp.repositories.role_module_repositories.ModuleRepository;
import com.prebel.prototipo.webapp.repositories.role_module_repositories.RoleModuleRepository;
import com.prebel.prototipo.webapp.repositories.role_module_repositories.RoleRepository;
import com.prebel.prototipo.webapp.repositories.role_module_repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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
    public ResponseEntity<String> createRole(@RequestBody RoleRequestDTO request) {
        // Validar si el rol ya existe
        if (roleRepository.findByRoleName(request.getRoleName()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("El rol ya existe");
        }
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

        return ResponseEntity.status(HttpStatus.CREATED).body("Rol creado exitosamente");
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateRoleModules(@RequestBody RoleRequestDTO request) {
        // Retrieve the role to update
        Role role = roleRepository.findByRoleName(request.getRoleName())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        // Handle adding/updating modules with permissions
        request.getModules().forEach(rmRequest -> {
            Module module = moduleRepository.findByName(rmRequest.getModuleName())
                    .orElseThrow(() -> new RuntimeException("Módulo no encontrado"));

            Optional<RoleModule> existingRoleModule = role.getRoleModules().stream()
                    .filter(rm -> rm.getModule().equals(module))
                    .findFirst();

            if (existingRoleModule.isPresent()) {
                RoleModule roleModule = existingRoleModule.get();
                roleModule.setPermissions(rmRequest.getPermissions());
                roleModuleRepository.save(roleModule);
            } else {
                RoleModule roleModule = new RoleModule();
                roleModule.setRole(role);
                roleModule.setModule(module);
                roleModule.setPermissions(rmRequest.getPermissions());
                roleModuleRepository.save(roleModule);
            }
        });

        if (request.getModulesToRemove() != null) {
            request.getModulesToRemove().forEach(moduleName -> {
                Module module = moduleRepository.findByName(moduleName)
                        .orElseThrow(() -> new RuntimeException("Módulo no encontrado"));

                roleModuleRepository.findByRoleAndModule(role, module)
                        .ifPresent(roleModuleRepository::delete);
            });
        }

        return ResponseEntity.ok("Rol actualizado correctamente");
    }

    @PostMapping("/user")
    public ResponseEntity<?> getUserPermissions(@RequestBody String userName) {
        Optional<User> userOptional = userRepository.findByName(userName);

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

        return ResponseEntity.ok(new UserRoleResponseDTO(user.getName(), role.getRoleName(), modulesWithPermissions));
    }

    @GetMapping
    public ResponseEntity<List<String>> getAllRoles() {
        List<String> roleNames = StreamSupport.stream(roleRepository.findAll().spliterator(), false)
                .map(Role::getRoleName)
                .toList();
        return ResponseEntity.ok(roleNames);
    }

    @PostMapping("/role_modules")
    public ResponseEntity<List<String>> getModulesFromRole(@RequestBody String roleName) {
        Role role = roleRepository.findByRoleName(roleName)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        List<String> roleModules = roleModuleRepository.findByRole(role).stream()
                .map(rm -> rm.getModule().getName())
                .collect(Collectors.toList());
        return ResponseEntity.ok(roleModules);
    }
}
