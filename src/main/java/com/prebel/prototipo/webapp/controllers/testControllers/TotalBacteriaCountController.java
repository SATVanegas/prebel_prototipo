package com.prebel.prototipo.webapp.controllers.testControllers;

import com.prebel.prototipo.webapp.models.tests.TotalBacteriaCount;
import com.prebel.prototipo.webapp.repositories.testRepositories.TotalBacteriaCountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/test/totalbacteriacount")
public class TotalBacteriaCountController {

    @Autowired
    private TotalBacteriaCountRepository totalBacteriaCountRepository;

    // Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<TotalBacteriaCount> getTotalBacteriaCountById(@PathVariable Long id) {
        Optional<TotalBacteriaCount> totalBacteriaCount = totalBacteriaCountRepository.findById(id);
        return totalBacteriaCount.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear un nuevo TotalBacteriaCount
    @PostMapping
    public ResponseEntity<TotalBacteriaCount> createTotalBacteriaCount(@RequestBody TotalBacteriaCount totalBacteriaCount) {
        if (totalBacteriaCount.getUnit() == null || totalBacteriaCount.getMethod() == null) {
            return ResponseEntity.badRequest().build();  // Evita crear registros con datos incompletos
        }
        TotalBacteriaCount savedTotalBacteriaCount = totalBacteriaCountRepository.save(totalBacteriaCount);
        return ResponseEntity.ok(savedTotalBacteriaCount);
    }
}
