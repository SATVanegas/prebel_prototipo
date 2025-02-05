package com.prebel.prototipo.webapp.controllers.testsControllers;

import com.prebel.prototipo.webapp.models.tests.SpecificGravity;
import com.prebel.prototipo.webapp.repositories.testRepositories.SpecificGravityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/test/specificgravity")
public class SpecificGravityController {

    @Autowired
    private SpecificGravityRepository specificGravityRepository;

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
