package com.prebel.prototipo.webapp.controllers.laboratory_reports_controllers.tests_controllers;

import com.prebel.prototipo.webapp.models.laboratory_reports.tests.Odor;
import com.prebel.prototipo.webapp.repositories.laboratory_reports_repositories.test_repositories.OdorRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/test/odor")
public class OdorController {

    private final OdorRepository odorRepository;

    public OdorController(OdorRepository odorRepository) {
        this.odorRepository = odorRepository;
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Odor> getOdorById(@PathVariable Long id) {
        Optional<Odor> odor = odorRepository.findById(id);
        return odor.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear un nuevo Odor
    @PostMapping
    public ResponseEntity<Odor> createOdor(@RequestBody Odor odor) {
        if (odor.getUnit() == null) {
            return ResponseEntity.badRequest().build();  // Evita crear registros con datos incompletos
        }
        Odor savedOdor = odorRepository.save(odor);
        return ResponseEntity.ok(savedOdor);
    }
}
