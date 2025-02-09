package com.prebel.prototipo.webapp.controllers.laboratory_reports_controllers.tests_controllers;

import com.prebel.prototipo.webapp.dtos.validations.TestTemperatureDTO;
import com.prebel.prototipo.webapp.models.laboratory_reports.tests.Temperature;
import com.prebel.prototipo.webapp.repositories.laboratory_reports_repositories.test_repositories.TemperatureRepository;
import jakarta.validation.Valid;
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
    public ResponseEntity<String> createTemperature(@Valid @RequestBody TestTemperatureDTO dto) {
        temperatureRepository.save(new Temperature(dto));
        return ResponseEntity.ok("Test creado correctamente");
    }
}
