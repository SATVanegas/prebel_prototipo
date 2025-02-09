package com.prebel.prototipo.webapp.controllers.laboratory_reports_controllers;

import com.prebel.prototipo.webapp.dtos.validations.TestDTO;
import com.prebel.prototipo.webapp.models.laboratory_reports.Test;
import com.prebel.prototipo.webapp.repositories.laboratory_reports_repositories.TestRepository;
import com.prebel.prototipo.webapp.services.TestService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/test")
public class TestController {

    private final TestRepository testRepository;
    private final TestService testService;

    public TestController(TestRepository testRepository, TestService testService) {
        this.testRepository = testRepository;
        this.testService = testService;
    }

    // Buscar un Test por ID
    @GetMapping("/{id}")
    public ResponseEntity<Test> getTestById(@PathVariable Long id) {
        Optional<Test> test = testRepository.findById(id);
        return test.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear un nuevo Test
    @PostMapping
    public ResponseEntity<String> createTest(@Valid @RequestBody TestDTO dto) {
        testService.createTest(dto);
        return ResponseEntity.ok("Test creado correctamente");
    }

}