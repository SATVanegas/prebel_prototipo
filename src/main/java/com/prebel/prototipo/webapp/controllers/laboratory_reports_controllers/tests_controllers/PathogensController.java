package com.prebel.prototipo.webapp.controllers.laboratory_reports_controllers.tests_controllers;

import com.prebel.prototipo.webapp.models.laboratory_reports.tests.Pathogens;
import com.prebel.prototipo.webapp.repositories.laboratory_reports_repositories.test_repositories.PathogensRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/test/pathogens")
public class PathogensController {

    private final PathogensRepository pathogensRepository;

    public PathogensController(PathogensRepository pathogensRepository) {
        this.pathogensRepository = pathogensRepository;
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Pathogens> getPathogensById(@PathVariable Long id) {
        Optional<Pathogens> pathogens = pathogensRepository.findById(id);
        return pathogens.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear un nuevo Pathogens
    @PostMapping
    public ResponseEntity<Pathogens> createPathogens(@RequestBody Pathogens pathogens) {
        if (pathogens.getUnit() == null) {
            return ResponseEntity.badRequest().build();  // Evita crear registros con datos incompletos
        }
        Pathogens savedPathogens = pathogensRepository.save(pathogens);
        return ResponseEntity.ok(savedPathogens);
    }
}
