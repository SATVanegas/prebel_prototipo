package com.prebel.prototipo.webapp.controllers.weekly_planner_controllers;

import com.prebel.prototipo.webapp.services.WeeklyCalendarService;
import com.prebel.prototipo.webapp.models.weekly_planner.TechnicianSchedule;
import com.prebel.prototipo.webapp.models.weekly_planner.WeeklyCalendar;
import com.prebel.prototipo.webapp.repositories.weekly_planner_repositories.WeeklyCalendarRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/weeklycalendar")
public class WeeklyCalendarController {

    private final WeeklyCalendarRepository weeklyCalendarRepository;
    private final WeeklyCalendarService weeklyCalendarService;

    public WeeklyCalendarController(WeeklyCalendarRepository weeklyCalendarRepository,  WeeklyCalendarService weeklyCalendarService) {
        this.weeklyCalendarRepository = weeklyCalendarRepository;
        this.weeklyCalendarService = weeklyCalendarService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createWeeklyCalendar(@RequestBody Map<String, Object> requestBody) {
        try {
            // Obtener los IDs de los TechnicianSchedules
            List<Integer> technicianScheduleIds = (List<Integer>) requestBody.get("technicianScheduleIds");

            if (technicianScheduleIds == null || technicianScheduleIds.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Debe proporcionar al menos un ID de TechnicianSchedule.");
            }

            // Validar los TechnicianSchedules
            List<TechnicianSchedule> technicianSchedules = weeklyCalendarService.validateTechnicianSchedules(technicianScheduleIds);

            // Crear el WeeklyCalendar
            WeeklyCalendar savedWeeklyCalendar = weeklyCalendarService.createWeeklyCalendar(technicianSchedules);

            return ResponseEntity.status(HttpStatus.CREATED).body(savedWeeklyCalendar);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error procesando la solicitud: " + e.getMessage());
        }
    }

    // Obtener el calendario semanal por id
    @GetMapping("/view/{id}")
    public ResponseEntity<WeeklyCalendar> getWeeklyCalendarById(@PathVariable Long id) {
        return weeklyCalendarRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}