package com.prebel.prototipo.webapp.controllers.laboratory_reports_controllers.tests_controllers;


import com.prebel.prototipo.webapp.models.laboratory_reports.tests.FungiYeastCount;
import com.prebel.prototipo.webapp.repositories.laboratory_reports_repositories.test_repositories.FungiYeastCountRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/test/fungiyeastcount")
public class FungiYeastCountController {

    private final FungiYeastCountRepository fungiYeastCountRepository;

    public FungiYeastCountController(FungiYeastCountRepository fungiYeastCountRepository) {
        this.fungiYeastCountRepository = fungiYeastCountRepository;
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<FungiYeastCount> getFungiYeastCountById(@PathVariable Long id) {
        Optional<FungiYeastCount> fungiYeastCount = fungiYeastCountRepository.findById(id);
        return fungiYeastCount.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear un nuevo FungiYeastCount
    @PostMapping
    public ResponseEntity<FungiYeastCount> createFungiYeastCount(@RequestBody FungiYeastCount fungiYeastCount) {
        if (fungiYeastCount.getUnit() == null) {
            return ResponseEntity.badRequest().build();  // Evita crear registros con datos incompletos
        }
        FungiYeastCount savedFungiYeastCount = fungiYeastCountRepository.save(fungiYeastCount);
        return ResponseEntity.ok(savedFungiYeastCount);
    }
}
