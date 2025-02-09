package com.prebel.prototipo.webapp.controllers.laboratory_reports_controllers.tests_controllers;

import com.prebel.prototipo.webapp.dtos.validations.TestsDTO;
import com.prebel.prototipo.webapp.models.laboratory_reports.tests.pH;
import com.prebel.prototipo.webapp.repositories.laboratory_reports_repositories.test_repositories.pHRepository;
import jakarta.validation.Valid;
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
    public ResponseEntity<String> createPH(@Valid @RequestBody TestsDTO dto) {
        phRepository.save(new pH(dto));
        return ResponseEntity.ok("Test creado correctamente");
    }
}
