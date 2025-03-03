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
    public ResponseEntity<TestDTO> getTestDTOById(@PathVariable Long id) {
        Optional<TestDTO> test = testService.getTestDTOById(id);
        return test.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear un nuevo Test
    @PostMapping
    public ResponseEntity<Long> createTest(@RequestBody @Valid TestDTO testDTO) {
        Test test = testService.createTest(testDTO);
        Long testId = test.getId(); // Establecer el ID del Test creado en el DTO
        return ResponseEntity.ok(testId);
    }

}