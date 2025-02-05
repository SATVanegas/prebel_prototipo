package com.prebel.prototipo.webapp.controllers.weekly_planner_controllers;

import com.prebel.prototipo.webapp.models.weekly_planner.TechnicianSchedule;
import com.prebel.prototipo.webapp.models.weekly_planner.WeeklyCalendar;
import com.prebel.prototipo.webapp.repositories.weekly_planner_repositories.WeeklyCalendarRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
/*
FUNCIONES:
    -POR SEMANA:
        Obtener el calendario semanal por id
        Crear un nuevo calendario semanal
        Actualizar la programacion de un calendario semanal(Completo)
        Eliminar un calendario semanal(Completo)
    -POR DIA:
        Obtener la programacion de un dia de la semana
        Actualizar la programacion de un dia del calendario semanal
        Eliminar la programacion de un dia de la semana
 */
@RestController
@RequestMapping("/api/weeklycalendar")
public class WeeklyCalendarController {

    private final WeeklyCalendarRepository weeklyCalendarRepository;

    public WeeklyCalendarController(WeeklyCalendarRepository weeklyCalendarRepository) {
        this.weeklyCalendarRepository = weeklyCalendarRepository;
    }

    // Obtener el calendario semanal por id
    @GetMapping("/view/{id}")
    public ResponseEntity<WeeklyCalendar> getWeeklyCalendarById(@PathVariable Long id) {
        return weeklyCalendarRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    //Obtener la programacion de un dia de la semana
    @GetMapping("/viewday/{day}")
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
    @PostMapping("/create")
    public ResponseEntity<WeeklyCalendar> createWeeklyCalendar(@RequestBody WeeklyCalendar weeklyCalendar) {
        WeeklyCalendar savedWeeklyCalendar = weeklyCalendarRepository.save(weeklyCalendar);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedWeeklyCalendar);
    }

    // Actualizar un calendario semanal
    @PutMapping("/update/{id}")
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

    // Actualizar un dia del calendario semanal
    @PutMapping("/updateday/{id}/{day}")
    public ResponseEntity<WeeklyCalendar> updateDayTask(@PathVariable Long id, @PathVariable String day, @RequestBody List<TechnicianSchedule> updatedSchedules) {
        Optional<WeeklyCalendar> weeklyCalendar = weeklyCalendarRepository.findById(id);

        if (weeklyCalendar.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        WeeklyCalendar updatedWeeklyCalendar = weeklyCalendarRepository.updateDayTasks(weeklyCalendar.get(), day, updatedSchedules);

        if (updatedWeeklyCalendar != null) {
            return ResponseEntity.ok(updatedWeeklyCalendar);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    // Eliminar un calendario semanal
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteWeeklyCalendar(@PathVariable Long id) {
        return weeklyCalendarRepository.findById(id)
                .map(existingCalendar -> {
                    weeklyCalendarRepository.delete(existingCalendar);
                    return ResponseEntity.noContent().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Eliminar la programacion de un dia de la semana
    @DeleteMapping("/deleteday/{id}/{day}")
    public ResponseEntity<Void> deleteDayTask(@PathVariable Long id, @PathVariable String day) {
        Optional<WeeklyCalendar> weeklyCalendar = weeklyCalendarRepository.findById(id);

        if (weeklyCalendar.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        boolean updated = weeklyCalendarRepository.deleteDayTasks(weeklyCalendar.get(), day);

        if (updated) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }


}