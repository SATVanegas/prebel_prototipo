package com.prebel.prototipo.webapp.controllers.laboratory_reports_controllers.tests_controllers;

import com.prebel.prototipo.webapp.dtos.validations.laboratory_reports_requests.TestConditionDTO;
import com.prebel.prototipo.webapp.models.laboratory_reports.tests.Condition;
import com.prebel.prototipo.webapp.services.laboratory_reports_services.test_services.ConditionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/test/conditions")
public class ConditionController {

    private final ConditionService conditionService;

    public ConditionController(ConditionService conditionService) {
        this.conditionService = conditionService;
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Condition> getConditionById(@PathVariable Long id) {
        Optional<Condition> condition = conditionService.getCondition(id);
        return condition.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear un nuevo Condition
    @PostMapping
    public ResponseEntity<String> createCondition(@Valid @RequestBody TestConditionDTO dto) {
        conditionService.createCondition(dto);
        return ResponseEntity.ok("Test creado correctamente");
    }
}
