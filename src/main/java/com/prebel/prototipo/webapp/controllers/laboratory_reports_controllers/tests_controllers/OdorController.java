package com.prebel.prototipo.webapp.controllers.laboratory_reports_controllers.tests_controllers;

import com.prebel.prototipo.webapp.dtos.validations.TestsDTO;
import com.prebel.prototipo.webapp.models.laboratory_reports.tests.Odor;
import com.prebel.prototipo.webapp.repositories.laboratory_reports_repositories.test_repositories.OdorRepository;
import jakarta.validation.Valid;
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
    public ResponseEntity<String> createOdor(@Valid @RequestBody TestsDTO dto) {
        odorRepository.save(new Odor(dto));
        return ResponseEntity.ok("Test creado correctamente");
    }
}
