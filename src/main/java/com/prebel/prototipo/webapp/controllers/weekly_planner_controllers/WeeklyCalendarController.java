package com.prebel.prototipo.webapp.controllers.weekly_planner_controllers;

import com.prebel.prototipo.webapp.models.weekly_planner.TechnicianSchedule;
import com.prebel.prototipo.webapp.models.weekly_planner.WeeklyCalendar;
import com.prebel.prototipo.webapp.repositories.weekly_planner_repositories.TechnicianScheduleRepository;
import com.prebel.prototipo.webapp.repositories.weekly_planner_repositories.WeeklyCalendarRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/weeklyplanner/weeklycalendar")
public class WeeklyCalendarController {


    private final WeeklyCalendarRepository weeklyCalendarRepository;
    private final TechnicianScheduleRepository technicianScheduleRepository;

    public WeeklyCalendarController(WeeklyCalendarRepository weeklyCalendarRepository, TechnicianScheduleRepository technicianScheduleRepository) {
        this.weeklyCalendarRepository = weeklyCalendarRepository;
        this.technicianScheduleRepository = technicianScheduleRepository;
    }

    // Obtener un WeeklyCalendar por ID
    @GetMapping("/{id}")
    public ResponseEntity<WeeklyCalendar> getWeeklyCalendarById(@PathVariable Long id) {
        Optional<WeeklyCalendar> calendar = weeklyCalendarRepository.findById(id);
        return calendar.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear un nuevo WeeklyCalendar
    @PostMapping
    public ResponseEntity<?> createWeeklyCalendar(@RequestBody WeeklyCalendar weeklyCalendar) {
        WeeklyCalendar savedCalendar = weeklyCalendarRepository.save(weeklyCalendar);
        return ResponseEntity.ok(savedCalendar);
    }
}



