package com.prebel.prototipo.webapp.controllers.testControllers;


import com.prebel.prototipo.webapp.models.Test;
import com.prebel.prototipo.webapp.repositories.testRepositories.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    private TestRepository testRepository;

    // Buscar un Test por ID
    @GetMapping("/{id}")
    public ResponseEntity<Test> getTestById(@PathVariable Long id) {
        Optional<Test> test = testRepository.findById(id);
        return test.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear un nuevo Test
    @PostMapping
    public ResponseEntity<Test> createTest(@RequestBody Test test) {
        if (test.getProduct() == null || test.getUserOrganolepticTests() == null || test.getUserPhysicochemicalTests() == null) {
            return ResponseEntity.badRequest().build();  // Validaciones básicas
        }
        Test savedTest = testRepository.save(test);
        return ResponseEntity.ok(savedTest);
    }
}