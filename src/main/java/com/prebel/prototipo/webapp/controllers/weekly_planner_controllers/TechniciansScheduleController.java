package com.prebel.prototipo.webapp.controllers.weekly_planner_controllers;

import com.prebel.prototipo.webapp.models.role_module.User;
import com.prebel.prototipo.webapp.models.weekly_planner.TechniciansSchedule;
import com.prebel.prototipo.webapp.repositories.role_module_repositories.UserRepository;
import com.prebel.prototipo.webapp.repositories.weekly_planner_repositories.TechniciansScheduleRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/weeklyplanner/techniciansschedule")
public class TechniciansScheduleController {

    private final TechniciansScheduleRepository techniciansScheduleRepository;
    private final UserRepository userRepository;

    public TechniciansScheduleController(TechniciansScheduleRepository techniciansScheduleRepository, UserRepository userRepository) {
        this.techniciansScheduleRepository = techniciansScheduleRepository;
        this.userRepository = userRepository;
    }

    // Obtener un TechniciansSchedule por ID
    @GetMapping("/{id}")
    public ResponseEntity<TechniciansSchedule> getTechniciansScheduleById(@PathVariable Long id) {
        Optional<TechniciansSchedule> schedule = techniciansScheduleRepository.findById(id);
        return schedule.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear un nuevo TechniciansSchedule
    @PostMapping
    public ResponseEntity<?> createTechniciansSchedule(@RequestBody TechniciansSchedule techniciansSchedule) {
        Optional<User> technicianOptional = userRepository.findById(techniciansSchedule.getTechnician().getId());
        if (technicianOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("El técnico especificado no existe.");
        }

        techniciansSchedule.setTechnician(technicianOptional.get());
        TechniciansSchedule savedSchedule = techniciansScheduleRepository.save(techniciansSchedule);
        return ResponseEntity.ok(savedSchedule);
    }
}


