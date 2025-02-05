package com.prebel.prototipo.webapp.controllers.testsControllers;

import com.prebel.prototipo.webapp.models.tests.pH;
import com.prebel.prototipo.webapp.repositories.testRepositories.pHRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/test/ph")
public class pHController {

    @Autowired
    private pHRepository phRepository;

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
