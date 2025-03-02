package com.prebel.prototipo.webapp.controllers.weekly_planner_controllers;

import com.prebel.prototipo.webapp.dtos.updates.TechnicianScheduleUpdateDTO;
import com.prebel.prototipo.webapp.dtos.validations.weekly_planner_request.TechnicianScheduleDTO;
import com.prebel.prototipo.webapp.models.weekly_planner.TechnicianSchedule;
import com.prebel.prototipo.webapp.services.weekly_planner_services.TechnicianScheduleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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
        try {
            Optional<TechnicianSchedule> technicianSchedule = technicianScheduleService.getTechnicianSchedule(id);
            return technicianSchedule.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Crear un nuevo technicianSchedule
    @PostMapping
    public ResponseEntity<String> createTechnicianSchedule(@Valid @RequestBody TechnicianScheduleDTO dto) {
        try {
            technicianScheduleService.createTechnicianSchedule(dto);
            return ResponseEntity.ok("Technician Schedule creado correctamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al crear Technician Schedule: " + e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<TechnicianSchedule>> getAllTechnicianSchedule() {
        try {
            List<TechnicianSchedule> technicianSchedules = technicianScheduleService.getAllTechnicianSchedules();
            return technicianSchedules.isEmpty()
                    ? ResponseEntity.noContent().build()
                    : ResponseEntity.ok(technicianSchedules);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updateTechnicianSchedule(
            @PathVariable Long id,
            @Valid @RequestBody TechnicianScheduleUpdateDTO dto) {
        try {
            technicianScheduleService.updateTechnicianSchedule(id, dto);
            return ResponseEntity.ok("Technician Schedule actualizado correctamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al actualizar: " + e.getMessage());
        }
    }

    // Obtener programaciones por técnico
    @GetMapping("/technician/{technicianId}")
    public ResponseEntity<?> getTechnicianSchedulesByTechnicianId(@PathVariable Long technicianId) {
        try {
            List<TechnicianSchedule> schedules = technicianScheduleService.getTechnicianSchedulesByTechnicianId(technicianId);
            return schedules.isEmpty()
                    ? ResponseEntity.noContent().build()
                    : ResponseEntity.ok(schedules);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener programaciones: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTechnicianSchedule(@PathVariable Long id) {
        try {
            boolean deleted = technicianScheduleService.deleteTechnicianSchedule(id);
            return deleted
                    ? ResponseEntity.ok("Technician Schedule eliminado correctamente")
                    : ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar: " + e.getMessage());
        }
    }
}


