package com.prebel.prototipo.webapp.controllers.laboratory_reports_controllers.tests_controllers;

import com.prebel.prototipo.webapp.models.laboratory_reports.tests.SpecificGravity;
import com.prebel.prototipo.webapp.repositories.laboratory_reports_repositories.test_repositories.SpecificGravityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/test/specificgravity")
public class SpecificGravityController {

    private final SpecificGravityRepository specificGravityRepository;

    public SpecificGravityController(SpecificGravityRepository specificGravityRepository) {
        this.specificGravityRepository = specificGravityRepository;
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<SpecificGravity> getSpecificGravityById(@PathVariable Long id) {
        Optional<SpecificGravity> specificGravity = specificGravityRepository.findById(id);
        return specificGravity.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    //  Crear un nuevo SpecificGravity
    @PostMapping
    public ResponseEntity<SpecificGravity> createSpecificGravity(@RequestBody SpecificGravity specificGravity) {
        if (specificGravity.getUnit() == null) {
            return ResponseEntity.badRequest().build();  // Evita crear registros con datos incompletos
        }
        SpecificGravity savedSpecificGravity = specificGravityRepository.save(specificGravity);
        return ResponseEntity.ok(savedSpecificGravity);
    }
}
