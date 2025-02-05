package com.prebel.prototipo.webapp.controllers.weekly_planner_controllers;

import com.prebel.prototipo.webapp.models.role_module.Role;
import com.prebel.prototipo.webapp.models.role_module.User;
import com.prebel.prototipo.webapp.models.weekly_planner.DayWeek;
import com.prebel.prototipo.webapp.models.weekly_planner.TechnicianSchedule;
import com.prebel.prototipo.webapp.repositories.role_module_repositories.UserRepository;
import com.prebel.prototipo.webapp.repositories.weekly_planner_repositories.TechnicianScheduleRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;
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
    public ResponseEntity<?> createTechniciansSchedule(@RequestBody Map<String, Object> requestBody) {
        try {
            Integer technicianId = (Integer) requestBody.get("technicianId");
            String dayName = (String) requestBody.get("day");

            if (technicianId == null || dayName == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Faltan datos en la solicitud.");
            }

            // Buscar al técnico en la base de datos
            Optional<User> technicianOptional = userRepository.findById(Long.valueOf(technicianId));
            if (technicianOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("El técnico especificado no existe.");
            }

            // Obtener el rol del técnico
            Role assignedRole = technicianOptional.get().getRole(); // Asegúrate de tener el método 'getRole()' en la clase User

            if (assignedRole == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("El técnico no tiene un rol asignado.");
            }

            // Convertir el nombre del día a enum
            DayWeek day;
            try {
                day = Arrays.stream(DayWeek.values())
                        .filter(d -> d.toString().equalsIgnoreCase(dayName.trim()))
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("Día de la semana no válido."));
            } catch (IllegalArgumentException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Día de la semana no válido: " + dayName);
            }

            // Crear el objeto TechnicianSchedule y asignar el rol
            TechnicianSchedule techniciansSchedule = new TechnicianSchedule();
            techniciansSchedule.setTechnician(technicianOptional.get());
            techniciansSchedule.setDay(day);
            techniciansSchedule.setAssignedRole(assignedRole); // Asignar el rol aquí

            TechnicianSchedule savedSchedule = technicianScheduleRepository.save(techniciansSchedule);
            return ResponseEntity.ok(savedSchedule);

        } catch (Exception e) {
            e.printStackTrace();  // Esto imprimirá la traza completa del error en los logs
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error procesando la solicitud: " + e.getMessage());
        }
    }


}


