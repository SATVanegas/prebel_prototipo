package com.prebel.prototipo.webapp.controllers.weekly_planner_controllers;

import com.prebel.prototipo.webapp.dtos.CreateTaskDTO;
import com.prebel.prototipo.webapp.dtos.ViewTaskRequestDTO;
import com.prebel.prototipo.webapp.models.role_module.User;
import com.prebel.prototipo.webapp.models.role_module.Role;
import com.prebel.prototipo.webapp.models.weekly_planner.WeeklyCalendar;
import com.prebel.prototipo.webapp.repositories.role_module_repositories.RoleRepository;
import com.prebel.prototipo.webapp.repositories.role_module_repositories.UserRepository;
import com.prebel.prototipo.webapp.repositories.weekly_planner_repositories.WeeklyCalendarRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/weeklycalendar")
public class WeeklyCalendarController {
    private final WeeklyCalendarRepository weeklyCalendarRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public WeeklyCalendarController(WeeklyCalendarRepository weeklyCalendarRepository,
                                    UserRepository userRepository,
                                    RoleRepository roleRepository) {
        this.weeklyCalendarRepository = weeklyCalendarRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @GetMapping
    public List<WeeklyCalendar> getAllTasks() {
        return (List<WeeklyCalendar>) weeklyCalendarRepository.findAll();
    }

    @GetMapping("/tasks")
    public ResponseEntity<List<WeeklyCalendar>> getTasks(@RequestBody ViewTaskRequestDTO requestDTO) {
        if (requestDTO.getUserName() != null && !requestDTO.getUserName().isEmpty()) {
            // Si el nombre de usuario está presente, buscar las tareas asignadas a ese usuario
            Optional<User> user = userRepository.findByName(requestDTO.getUserName());
            if (user.isPresent()) {
                List<WeeklyCalendar> tasks = weeklyCalendarRepository.findByAssignedUser(user.get());
                return ResponseEntity.ok(tasks);
            }
        } else if (requestDTO.getRoleName() != null && !requestDTO.getRoleName().isEmpty()) {
            // Si el nombre de rol está presente, buscar las tareas asignadas a ese rol
            Optional<Role> role = roleRepository.findByRoleName(requestDTO.getRoleName());
            if (role.isPresent()) {
                List<WeeklyCalendar> tasks = weeklyCalendarRepository.findByAssignedRole(role.get());
                return ResponseEntity.ok(tasks);
            }
        }
        return ResponseEntity.notFound().build();
    }


    @PostMapping("/create")
    public ResponseEntity<WeeklyCalendar> createTask(@RequestBody CreateTaskDTO taskDTO) {
        // Validar campos obligatorios
        if (taskDTO.getTitle() == null || taskDTO.getDescription() == null || taskDTO.getStartDate() == null || taskDTO.getEndDate() == null) {
            return ResponseEntity.badRequest().body(null);  // Faltan campos obligatorios
        }

        WeeklyCalendar task = new WeeklyCalendar();
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setStartDate(taskDTO.getStartDate());
        task.setEndDate(taskDTO.getEndDate());

        // Verificar si se proporciona el nombre de un usuario o rol
        if (taskDTO.getUserName() != null && taskDTO.getRoleName() != null) {
            return ResponseEntity.badRequest().body(null);  // No se puede proporcionar ambos
        }

        if (taskDTO.getUserName() != null) {
            // Buscar usuario por nombre
            Optional<User> user = userRepository.findByName(taskDTO.getUserName());
            if (user.isPresent()) {
                task.setAssignedUser(user.get());
                task.setAssignedRole(null);  // No se asigna rol
            } else {
                return ResponseEntity.badRequest().body(null);  // Usuario no encontrado
            }
        } else if (taskDTO.getRoleName() != null) {
            // Buscar rol por nombre
            Optional<Role> role = roleRepository.findByRoleName(taskDTO.getRoleName());
            if (role.isPresent()) {
                task.setAssignedRole(role.get());
                task.setAssignedUser(null);  // No se asigna usuario
            } else {
                return ResponseEntity.badRequest().body(null);  // Rol no encontrado
            }
        } else {
            return ResponseEntity.badRequest().body(null);  // Ningún usuario ni rol proporcionado
        }

        // Guardar la tarea
        WeeklyCalendar savedTask = weeklyCalendarRepository.save(task);
        return ResponseEntity.ok(savedTask);
    }


    @PutMapping("/{taskId}")
    public ResponseEntity<WeeklyCalendar> updateTask(@PathVariable Long taskId, @RequestBody WeeklyCalendar updatedTask) {
        return weeklyCalendarRepository.findById(taskId)
                .map(task -> {
                    task.setTitle(updatedTask.getTitle());
                    task.setDescription(updatedTask.getDescription());
                    task.setStartDate(updatedTask.getStartDate());
                    task.setEndDate(updatedTask.getEndDate());
                    WeeklyCalendar savedTask = weeklyCalendarRepository.save(task);
                    return ResponseEntity.ok(savedTask);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
        if (weeklyCalendarRepository.existsById(taskId)) {
            weeklyCalendarRepository.deleteById(taskId);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<Void> deleteTasksByUser(@PathVariable Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            weeklyCalendarRepository.deleteByAssignedUser(user.get());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/role/{roleId}")
    public ResponseEntity<Void> deleteTasksByRole(@PathVariable Long roleId) {
        Optional<Role> role = roleRepository.findById(roleId);
        if (role.isPresent()) {
            weeklyCalendarRepository.deleteByAssignedRole(role.get());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}


