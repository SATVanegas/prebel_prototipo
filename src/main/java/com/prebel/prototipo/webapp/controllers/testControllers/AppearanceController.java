package com.prebel.prototipo.webapp.controllers.testControllers;

import com.prebel.prototipo.webapp.models.tests.Appearance;
import com.prebel.prototipo.webapp.repositories.testRepositories.AppearanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/test/appearances")
public class AppearanceController {

    private final AppearanceRepository appearanceRepository;

    public AppearanceController(AppearanceRepository appearanceRepository) {
        this.appearanceRepository = appearanceRepository;
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Appearance> getAppearanceById(@PathVariable Long id) {
        Optional<Appearance> appearance = appearanceRepository.findById(id);
        return appearance.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }



    // Crear una nueva Appearance
    @PostMapping
    public ResponseEntity<Appearance> createAppearance(@RequestBody Appearance appearance) {
        if (appearance.getUnit() == null) {
            return ResponseEntity.badRequest().build();  // Evita crear registros con datos incompletos
        }
        Appearance savedAppearance = appearanceRepository.save(appearance);
        return ResponseEntity.ok(savedAppearance);
    }
}
