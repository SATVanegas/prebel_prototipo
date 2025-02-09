package com.prebel.prototipo.webapp.controllers.laboratory_reports_controllers.tests_controllers;

import com.prebel.prototipo.webapp.dtos.validations.TestsDTO;
import com.prebel.prototipo.webapp.models.laboratory_reports.tests.Viscosity;
import com.prebel.prototipo.webapp.repositories.laboratory_reports_repositories.test_repositories.ViscosityRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/test/viscosity")
public class ViscosityController {

    private final ViscosityRepository viscosityRepository;

    public ViscosityController(ViscosityRepository viscosityRepository) {
        this.viscosityRepository = viscosityRepository;
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Viscosity> getViscosityById(@PathVariable Long id) {
        Optional<Viscosity> viscosity = viscosityRepository.findById(id);
        return viscosity.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear un nuevo Viscosity
    @PostMapping
    public ResponseEntity<String> createViscosity(@Valid @RequestBody TestsDTO dto) {
        viscosityRepository.save(new Viscosity(dto));
        return ResponseEntity.ok("Test creado correctamente");
    }
}