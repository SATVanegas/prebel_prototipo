package com.prebel.prototipo.webapp.controllers.laboratory_reports_controllers.tests_controllers;

import com.prebel.prototipo.webapp.dtos.validations.laboratory_reports_requests.test_request.TestDTO;
import com.prebel.prototipo.webapp.models.laboratory_reports.tests.Test;
import com.prebel.prototipo.webapp.services.laboratory_reports_services.test_services.TestService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/test")
public class TestController {

    private final TestService testService;

    public TestController(TestService testService) {this.testService = testService;}

    // Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Test> getTestById(@PathVariable Long id) {
        Optional<Test> test = testService.getTestById(id);
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