package com.prebel.prototipo.webapp.controllers.weekly_planner_controllers;

import com.prebel.prototipo.webapp.models.weekly_planner.TechniciansSchedule;
import com.prebel.prototipo.webapp.models.weekly_planner.WeeklyCalendar;
import com.prebel.prototipo.webapp.repositories.weekly_planner_repositories.TechniciansScheduleRepository;
import com.prebel.prototipo.webapp.repositories.weekly_planner_repositories.WeeklyCalendarRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/weeklyplanner/weeklycalendar")
public class WeeklyCalendarController {

    private final WeeklyCalendarRepository weeklyCalendarRepository;
    private final TechniciansScheduleRepository techniciansScheduleRepository;

    public WeeklyCalendarController(WeeklyCalendarRepository weeklyCalendarRepository, TechniciansScheduleRepository techniciansScheduleRepository) {
        this.weeklyCalendarRepository = weeklyCalendarRepository;
        this.techniciansScheduleRepository = techniciansScheduleRepository;
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
        Optional<TechniciansSchedule> scheduleOptional = techniciansScheduleRepository.findById(weeklyCalendar.getTechniciansSchedule().getId());
        if (scheduleOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Se necesita ingresar los horarios..");
        }

        weeklyCalendar.setTechniciansSchedule(scheduleOptional.get());
        WeeklyCalendar savedCalendar = weeklyCalendarRepository.save(weeklyCalendar);
        return ResponseEntity.ok(savedCalendar);
    }
}


