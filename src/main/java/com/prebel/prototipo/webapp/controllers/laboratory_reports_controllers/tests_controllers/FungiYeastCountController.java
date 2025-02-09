package com.prebel.prototipo.webapp.controllers.laboratory_reports_controllers.tests_controllers;


import com.prebel.prototipo.webapp.dtos.validations.TestsDTO;
import com.prebel.prototipo.webapp.models.laboratory_reports.tests.FungiYeastCount;
import com.prebel.prototipo.webapp.repositories.laboratory_reports_repositories.test_repositories.FungiYeastCountRepository;
import jakarta.validation.Valid;
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
    public ResponseEntity<String> createFungiYeastCount(@Valid @RequestBody TestsDTO dto) {
        fungiYeastCountRepository.save(new FungiYeastCount(dto));
        return ResponseEntity.ok("Test creado correctamente");
    }
}
