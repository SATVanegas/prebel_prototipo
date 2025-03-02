package com.prebel.prototipo.webapp.controllers.weekly_planner_controllers;

import com.prebel.prototipo.webapp.dtos.validations.weekly_planner_request.WeeklyCalendarDTO;
import com.prebel.prototipo.webapp.services.weekly_planner_services.WeeklyCalendarService;
import com.prebel.prototipo.webapp.models.weekly_planner.WeeklyCalendar;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/weeklycalendar")
public class WeeklyCalendarController {

    private final WeeklyCalendarService weeklyCalendarService;

    public WeeklyCalendarController(WeeklyCalendarService weeklyCalendarService) {
        this.weeklyCalendarService = weeklyCalendarService;
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<WeeklyCalendar> getWeeklyCalendarById(@PathVariable Long id) {
        Optional<WeeklyCalendar> weeklyCalendar = weeklyCalendarService.getWeeklyCalendarById(id);
        return weeklyCalendar.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear un nuevo Condition
    @PostMapping
    public ResponseEntity<String> createWeeklyCalendar(@Valid @RequestBody WeeklyCalendarDTO dto) {
        weeklyCalendarService.createWeeklyCalendar(dto);
        return ResponseEntity.ok("Test creado correctamente");
    }

    // Añadir al WeeklyCalendarController.java
    @GetMapping
    public ResponseEntity<List<WeeklyCalendar>> getAllWeeklyCalendars() {
        List<WeeklyCalendar> calendars = weeklyCalendarService.getAllWeeklyCalendars();
        return ResponseEntity.ok(calendars);
    }

}