package com.prebel.prototipo.webapp.controllers.testsControllers;

import com.prebel.prototipo.webapp.models.tests.Temperature;
import com.prebel.prototipo.webapp.repositories.testRepositories.TemperatureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/test/temperature")
public class TemperatureController {

    @Autowired
    private TemperatureRepository temperatureRepository;

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
