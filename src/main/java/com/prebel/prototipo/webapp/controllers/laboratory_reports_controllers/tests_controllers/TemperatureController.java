package com.prebel.prototipo.webapp.controllers.laboratory_reports_controllers.tests_controllers;

import com.prebel.prototipo.webapp.dtos.validations.laboratory_reports_requests.test_request.TestTemperatureDTO;
import com.prebel.prototipo.webapp.models.laboratory_reports.tests.Temperature;
import com.prebel.prototipo.webapp.services.laboratory_reports_services.test_services.TemperatureService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/test/temperature")
public class TemperatureController {

    private final TemperatureService temperatureService;

    public TemperatureController(TemperatureService temperatureService) {this.temperatureService = temperatureService;}

    // Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Temperature> getTemperatureById(@PathVariable Long id) {
        Optional<Temperature> temperature = temperatureService.getTemperatureById(id);
        return temperature.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear una nueva Temperature
    @PostMapping
    public ResponseEntity<Long> temperatureStorage(@Valid @RequestBody TestTemperatureDTO dto) {
        Temperature createdTemperature = temperatureService.createTemperature(dto);
        return ResponseEntity.ok(createdTemperature.getId());
    }
}
