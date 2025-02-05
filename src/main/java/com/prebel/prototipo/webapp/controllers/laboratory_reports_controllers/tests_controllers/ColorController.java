package com.prebel.prototipo.webapp.controllers.laboratory_reports_controllers.tests_controllers;

import com.prebel.prototipo.webapp.models.laboratory_reports.tests.Color;
import com.prebel.prototipo.webapp.repositories.laboratory_reports_repositories.test_repositories.ColorRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/test/color")
public class ColorController {

    private final ColorRepository colorRepository;

    public ColorController(ColorRepository colorRepository) {
        this.colorRepository = colorRepository;
    }
    // Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Color> getColorById(@PathVariable Long id) {
        Optional<Color> color = colorRepository.findById(id);
        return color.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear un nuevo Color
    @PostMapping
    public ResponseEntity<Color> createColor(@RequestBody Color color) {
        if (color.getUnit() == null) {
            return ResponseEntity.badRequest().build();  // Evita crear registros con datos incompletos
        }
        Color savedColor = colorRepository.save(color);
        return ResponseEntity.ok(savedColor);
    }
}
