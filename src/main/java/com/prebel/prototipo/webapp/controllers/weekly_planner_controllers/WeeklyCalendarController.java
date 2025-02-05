package com.prebel.prototipo.webapp.controllers.weekly_planner_controllers;

import com.prebel.prototipo.webapp.models.weekly_planner.TechnicianSchedule;
import com.prebel.prototipo.webapp.models.weekly_planner.WeeklyCalendar;
import com.prebel.prototipo.webapp.repositories.weekly_planner_repositories.TechnicianScheduleRepository;
import com.prebel.prototipo.webapp.repositories.weekly_planner_repositories.WeeklyCalendarRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/weeklycalendar")
public class WeeklyCalendarController {

    private final WeeklyCalendarRepository weeklyCalendarRepository;
    private final TechnicianScheduleRepository technicianScheduleRepository;

    public WeeklyCalendarController(WeeklyCalendarRepository weeklyCalendarRepository, TechnicianScheduleRepository technicianScheduleRepository) {
        this.weeklyCalendarRepository = weeklyCalendarRepository;
        this.technicianScheduleRepository = technicianScheduleRepository;
    }

    // Obtener el calendario semanal por id
    @GetMapping("/{id}")
    public ResponseEntity<WeeklyCalendar> getWeeklyCalendarById(@PathVariable Long id) {
        return weeklyCalendarRepository.findById(id)
                .map(weeklyCalendar -> ResponseEntity.ok(weeklyCalendar))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/day/{day}")
    public ResponseEntity<List<TechnicianSchedule>> getDayTasks(@PathVariable String day) {
        Optional<WeeklyCalendar> weeklyCalendar = weeklyCalendarRepository.findById(1L); // Supongamos que 1L es el id del calendario actual

        if (weeklyCalendar.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<TechnicianSchedule> technicianSchedules = weeklyCalendarRepository.findTechnicianSchedulesByDay(weeklyCalendar.get(), day);

        if (technicianSchedules == null) {
            return ResponseEntity.badRequest().body(null);
        }

        return ResponseEntity.ok(technicianSchedules);
    }


    // Crear un nuevo calendario semanal
    @PostMapping
    public ResponseEntity<WeeklyCalendar> createWeeklyCalendar(@RequestBody WeeklyCalendar weeklyCalendar) {
        WeeklyCalendar savedWeeklyCalendar = weeklyCalendarRepository.save(weeklyCalendar);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedWeeklyCalendar);
    }

    // Actualizar un calendario semanal
    @PutMapping("/{id}")
    public ResponseEntity<WeeklyCalendar> updateWeeklyCalendar(@PathVariable Long id, @RequestBody WeeklyCalendar updatedWeeklyCalendar) {
        return weeklyCalendarRepository.findById(id)
                .map(existingCalendar -> {
                    existingCalendar.setStartDate(updatedWeeklyCalendar.getStartDate());
                    existingCalendar.setEndDate(updatedWeeklyCalendar.getEndDate());
                    // Puedes actualizar las listas de schedules si es necesario
                    WeeklyCalendar savedCalendar = weeklyCalendarRepository.save(existingCalendar);
                    return ResponseEntity.ok(savedCalendar);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Eliminar un calendario semanal
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteWeeklyCalendar(@PathVariable Long id) {
        return weeklyCalendarRepository.findById(id)
                .map(existingCalendar -> {
                    weeklyCalendarRepository.delete(existingCalendar);
                    return ResponseEntity.noContent().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}