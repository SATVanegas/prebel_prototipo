package com.prebel.prototipo.webapp.controllers.laboratory_reports_controllers.tests_controllers;

import com.prebel.prototipo.webapp.models.laboratory_reports.tests.Temperature;
import com.prebel.prototipo.webapp.repositories.laboratory_reports_repositories.test_repositories.TemperatureRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/test/temperature")
public class TemperatureController {

    private final TemperatureRepository temperatureRepository;

    public TemperatureController(TemperatureRepository temperatureRepository) {
        this.temperatureRepository = temperatureRepository;
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Temperature> getTemperatureById(@PathVariable Long id) {
        Optional<Temperature> temperature = temperatureRepository.findById(id);
        return temperature.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear un nuevo Temperature
    @PostMapping
    public ResponseEntity<Temperature> createTemperature(@RequestBody Temperature temperature) {
        if (temperature.getUnit() == null) {
            return ResponseEntity.badRequest().build();  // Evita crear registros con datos incompletos
        }
        Temperature savedTemperature = temperatureRepository.save(temperature);
        return ResponseEntity.ok(savedTemperature);
    }
}
