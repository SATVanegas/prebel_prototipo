package com.prebel.prototipo.webapp.controllers.testsControllers;

import com.prebel.prototipo.webapp.models.tests.Odor;
import com.prebel.prototipo.webapp.repositories.testRepositories.OdorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/test/odor")
public class OdorController {

    @Autowired
    private OdorRepository odorRepository;

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
