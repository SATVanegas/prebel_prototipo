package com.prebel.prototipo.webapp.controllers.laboratory_reports_controllers.tests_controllers;

import com.prebel.prototipo.webapp.models.laboratory_reports.tests.pH;
import com.prebel.prototipo.webapp.repositories.laboratory_reports_repositories.test_repositories.pHRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/test/ph")
public class pHController {

    private final pHRepository phRepository;

    public pHController(pHRepository phRepository) {
        this.phRepository = phRepository;
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<pH> getPHById(@PathVariable Long id) {
        Optional<pH> ph = phRepository.findById(id);
        return ph.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear un nuevo pH
    @PostMapping
    public ResponseEntity<pH> createPH(@RequestBody pH ph) {
        if (ph.getUnit() == null) {
            return ResponseEntity.badRequest().build();  // Evita crear registros con datos incompletos
        }
        pH savedPH = phRepository.save(ph);
        return ResponseEntity.ok(savedPH);
    }
}
