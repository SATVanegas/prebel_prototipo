package com.prebel.prototipo.webapp.controllers.weekly_planner_controllers;

import com.prebel.prototipo.webapp.models.User;
import com.prebel.prototipo.webapp.models.role_module.Role;
import com.prebel.prototipo.webapp.models.weekly_planner.WeeklyCalendar;
import com.prebel.prototipo.webapp.repositories.RoleRepository;
import com.prebel.prototipo.webapp.repositories.UserRepository;
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

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<WeeklyCalendar>> getTasksByUser(@PathVariable Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            List<WeeklyCalendar> tasks = weeklyCalendarRepository.findByAssignedUser(user.get());
            return ResponseEntity.ok(tasks);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/role/{roleId}")
    public ResponseEntity<List<WeeklyCalendar>> getTasksByRole(@PathVariable Long roleId) {
        Optional<Role> role = roleRepository.findById(roleId);
        if (role.isPresent()) {
            List<WeeklyCalendar> tasks = weeklyCalendarRepository.findByAssignedRole(role.get());
            return ResponseEntity.ok(tasks);
        }
        return ResponseEntity.notFound().build();
    }


    @PostMapping("/user/{userId}")
    public ResponseEntity<WeeklyCalendar> createTaskForUser(@PathVariable Long userId, @RequestBody WeeklyCalendar task) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            task.setAssignedUser(user.get());
            task.setAssignedRole(null);
            WeeklyCalendar savedTask = weeklyCalendarRepository.save(task);
            return ResponseEntity.ok(savedTask);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/role/{roleId}")
    public ResponseEntity<WeeklyCalendar> createTaskForRole(@PathVariable Long roleId, @RequestBody WeeklyCalendar task) {
        Optional<Role> role = roleRepository.findById(roleId);
        if (role.isPresent()) {
            task.setAssignedRole(role.get());
            task.setAssignedUser(null);
            WeeklyCalendar savedTask = weeklyCalendarRepository.save(task);
            return ResponseEntity.ok(savedTask);
        }
        return ResponseEntity.notFound().build();
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

    @GetMapping("/filter")
    public ResponseEntity<List<WeeklyCalendar>> getFilteredTasks(
            @RequestParam(required = false) Long roleId,
            @RequestParam(required = false) Long userId) {

        if (roleId != null) {
            return getTasksByRole(roleId);
        } else if (userId != null) {
            return getTasksByUser(userId);
        } else {
            return ResponseEntity.ok((List<WeeklyCalendar>) weeklyCalendarRepository.findAll());
        }
    }

    @GetMapping("/my-tasks/{userId}")
    public ResponseEntity<List<WeeklyCalendar>> getCombinedTasks(@PathVariable Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            List<WeeklyCalendar> tasks = new ArrayList<>();
            tasks.addAll(weeklyCalendarRepository.findByAssignedUser(user.get()));
            tasks.addAll(weeklyCalendarRepository.findByAssignedRole(user.get().getRole()));
            return ResponseEntity.ok(tasks);
        }
        return ResponseEntity.notFound().build();
    }
}


