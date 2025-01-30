package com.prebel.prototipo.webapp.controllers;

import com.prebel.prototipo.webapp.models.Role;
import com.prebel.prototipo.webapp.models.Roles;
import com.prebel.prototipo.webapp.models.User;
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

    public RolController(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<?> createRole(@RequestBody Role role) {
        // Verificar si el rol ya existe
        if (roleRepository.findByRoleEnum(role.getRoleEnum()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("El rol ya existe");
        }

        // Validar que el rol tenga permisos y descripción
        if (role.getPermissions() == null || role.getPermissions().isEmpty() ||
                role.getDescription() == null || role.getDescription().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Los permisos y la descripción son obligatorios");
        }

        // Guardar el nuevo rol en la base de datos
        Role savedRole = roleRepository.save(role);

        // Devolver una respuesta HTTP 201 (CREATED) con el rol guardado
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRole);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRole(@PathVariable long id, @RequestBody Role roleDetails) {
        Optional<Role> roleOptional = roleRepository.findById(id);

        if (roleOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Rol no encontrado");
        }

        Role role = roleOptional.get();

        if (roleDetails.getPermissions() != null && !roleDetails.getPermissions().isEmpty()) {
            role.setPermissions(roleDetails.getPermissions());
        }
        if (roleDetails.getDescription() != null && !roleDetails.getDescription().isEmpty()) {
            role.setDescription(roleDetails.getDescription());
        }

        Role updatedRole = roleRepository.save(role);

        return ResponseEntity.ok(updatedRole);
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

        return ResponseEntity.ok(role.getPermissions());
    }

    @GetMapping
    public ResponseEntity<List<Role>> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        return ResponseEntity.ok(roles);
    }

    @PostMapping("/assign-role/{userId}/{roleId}")
    public ResponseEntity<?> assignRoleToUser(@PathVariable long userId, @PathVariable long roleId) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuario no encontrado");
        }

        Optional<Role> roleOptional = roleRepository.findById(roleId);

        if (roleOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Rol no encontrado");
        }

        User user = userOptional.get();
        Role role = roleOptional.get();

        user.setRole(role);
        userRepository.save(user);

        return ResponseEntity.ok("Rol asignado correctamente al usuario");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable long id) {
        Optional<Role> roleOptional = roleRepository.findById(id);

        if (roleOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Rol no encontrado");
        }

        Role role = roleOptional.get();

        List<User> usersWithRole = userRepository.findByRole(role);

        if (!usersWithRole.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("No se puede eliminar el rol porque está asignado a uno o más usuarios");
        }
        
        roleRepository.delete(role);

        return ResponseEntity.ok("Rol eliminado correctamente");
    }
}
