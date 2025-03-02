package com.prebel.prototipo.webapp.controllers.weekly_planner_controllers;

import com.prebel.prototipo.webapp.dtos.request.weekly_planner_request.TechnicianScheduleDTO;
import com.prebel.prototipo.webapp.dtos.updates.weekly_planner_updates.TechnicianScheduleUpdateDTO;
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

    @GetMapping("/{id}")
    public ResponseEntity<TechnicianSchedule> getTechnicianScheduleById(@PathVariable Long id) {
        Optional<TechnicianSchedule> technicianSchedule = technicianScheduleService.getTechnicianSchedule(id);
        return technicianSchedule.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public ResponseEntity<List<TechnicianSchedule>> getAllTechnicianSchedule() {
        List<TechnicianSchedule> technicianSchedules = technicianScheduleService.getAllTechnicianSchedules();
        return technicianSchedules.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(technicianSchedules);
    }

    @GetMapping("/{weeklyCalendarId}/all_in_weekly_calendar")
    public ResponseEntity<List<TechnicianSchedule>> getAllTechnicianScheduleInWeeklyClendar(@PathVariable Long weeklyCalendarId) {
        List<TechnicianSchedule> technicianSchedules = technicianScheduleService.getAllTechnicianScheduleInWeeklyCalendar(weeklyCalendarId);
        return technicianSchedules.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(technicianSchedules);
    }

    @PostMapping
    public ResponseEntity<String> createTechnicianSchedule(@Valid @RequestBody TechnicianScheduleDTO dto) {
        technicianScheduleService.createTechnicianSchedule(dto);
        return ResponseEntity.ok("Technician Schedule creado correctamente");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TechnicianSchedule> updateTechnicianSchedule(
            @PathVariable Long id,
            @Valid @RequestBody TechnicianScheduleDTO dto) {
        technicianScheduleService.updateTechnicianSchedule(id, dto);
        return ResponseEntity.ok().build();
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTechnicianSchedule(@PathVariable Long id) {
        boolean deleted = technicianScheduleService.deleteTechnicianSchedule(id);

        return deleted
                ? ResponseEntity.ok("Technician Schedule eliminado correctamente")
                : ResponseEntity.notFound().build();
    }


}


