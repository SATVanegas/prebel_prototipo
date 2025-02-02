package com.prebel.prototipo.webapp.controllers.testControllers;

import com.prebel.prototipo.webapp.models.tests.Viscosity;
import com.prebel.prototipo.webapp.repositories.testRepositories.ViscosityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/test/viscosity")
public class ViscosityController {

    @Autowired
    private ViscosityRepository viscosityRepository;

    // Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Viscosity> getViscosityById(@PathVariable Long id) {
        Optional<Viscosity> viscosity = viscosityRepository.findById(id);
        return viscosity.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear un nuevo Viscosity
    @PostMapping
    public ResponseEntity<Viscosity> createViscosity(@RequestBody Viscosity viscosity) {
        if (viscosity.getUnit() == null || viscosity.getMethod() == null) {
            return ResponseEntity.badRequest().build();  // Evita crear registros inválidos
        }
        Viscosity savedViscosity = viscosityRepository.save(viscosity);
        return ResponseEntity.ok(savedViscosity);
    }
}