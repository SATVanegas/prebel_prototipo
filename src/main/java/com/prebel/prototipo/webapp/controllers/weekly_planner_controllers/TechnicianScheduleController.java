package com.prebel.prototipo.webapp.controllers.weekly_planner_controllers;

import com.prebel.prototipo.webapp.dtos.validations.weekly_planner_request.TechnicianScheduleDTO;
import com.prebel.prototipo.webapp.models.weekly_planner.TechnicianSchedule;
import com.prebel.prototipo.webapp.services.weekly_planner_services.TechnicianScheduleService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/weeklyplanner/techniciansschedule")
public class TechnicianScheduleController {
    private final TechnicianScheduleService technicianScheduleService;

    public TechnicianScheduleController(TechnicianScheduleService technicianScheduleService) {
        this.technicianScheduleService = technicianScheduleService;
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<TechnicianSchedule> getTechnicianScheduleById(@PathVariable Long id) {
        Optional<TechnicianSchedule> technicianSchedule = technicianScheduleService.getTechnicianSchedule(id);
        return technicianSchedule.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear un nuevo technicianSchedule
    @PostMapping
    public ResponseEntity<String> createTechnicianSchedule(@Valid @RequestBody TechnicianScheduleDTO dto) {
        technicianScheduleService.createTechnicianSchedule(dto);
        return ResponseEntity.ok("Technician Schedule creado correctamente");
    }

    @GetMapping("/all")
    public ResponseEntity<List<TechnicianSchedule>> getAllTechnicianSchedule() {
        List<TechnicianSchedule> technicianSchedules = technicianScheduleService.getAllTechnicianSchedules();
        return technicianSchedules.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(technicianSchedules);
    }


}


