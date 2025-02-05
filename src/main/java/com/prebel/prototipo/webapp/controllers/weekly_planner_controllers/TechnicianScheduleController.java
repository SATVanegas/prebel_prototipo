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
public class TechnicianScheduleController {

    private final TechnicianScheduleRepository technicianScheduleRepository;
    private final UserRepository userRepository;

    public TechnicianScheduleController(TechnicianScheduleRepository technicianScheduleRepository, UserRepository userRepository) {
        this.technicianScheduleRepository = technicianScheduleRepository;
        this.userRepository = userRepository;
    }

    // Obtener un TechnicianSchedule por ID
    @GetMapping("/{id}")
    public ResponseEntity<TechnicianSchedule> getTechniciansScheduleById(@PathVariable Long id) {
        Optional<TechnicianSchedule> schedule = technicianScheduleRepository.findById(id);
        return schedule.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear un nuevo TechnicianSchedule
    @PostMapping
    public ResponseEntity<?> createTechniciansSchedule(@RequestBody TechnicianSchedule techniciansSchedule) {
        Optional<User> technicianOptional = userRepository.findById(techniciansSchedule.getTechnician().getId());
        if (technicianOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("El técnico especificado no existe.");
        }

        techniciansSchedule.setTechnician(technicianOptional.get());
        TechnicianSchedule savedSchedule = technicianScheduleRepository.save(techniciansSchedule);
        return ResponseEntity.ok(savedSchedule);
    }
}


