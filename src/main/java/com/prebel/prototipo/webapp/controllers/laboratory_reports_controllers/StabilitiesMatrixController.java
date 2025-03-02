package com.prebel.prototipo.webapp.controllers.laboratory_reports_controllers;

import com.prebel.prototipo.webapp.dtos.validations.laboratory_reports_requests.StabilitiesMatrixDTO;
import com.prebel.prototipo.webapp.models.laboratory_reports.StabilitiesMatrix;
import com.prebel.prototipo.webapp.services.laboratory_reports_services.StabilitiesMatrixService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/stabilities-matrix")
public class StabilitiesMatrixController {

    private final StabilitiesMatrixService stabilitiesMatrixService;

    public StabilitiesMatrixController(StabilitiesMatrixService stabilitiesMatrixService) { this.stabilitiesMatrixService = stabilitiesMatrixService;}

    // Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<StabilitiesMatrix> getStabilitiesMatrixById(@PathVariable Long id) {
        Optional<StabilitiesMatrix> stabilitiesMatrix = stabilitiesMatrixService.getStabilitiesMatrixById(id);
        return stabilitiesMatrix.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear una nueva StabilitiesMatrix
    @PostMapping
    public ResponseEntity<String> createStabilitiesMatrix(@Valid @RequestBody StabilitiesMatrixDTO dto) {
        stabilitiesMatrixService.createStabilitiesMatrix(dto);
        return ResponseEntity.ok("Matriz de estabilidades creada correctamente");
    }


    // Endpoint para obtener inspecciones en los próximos 7 días
    @GetMapping("/upcoming-inspections")
    public ResponseEntity<List<StabilitiesMatrix>> getUpcomingInspections() {
        List<StabilitiesMatrix> upcomingInspections = stabilitiesMatrixService.getInspectionsDueInNext7Days();
        return ResponseEntity.ok(upcomingInspections);
    }
}


    @GetMapping("/product/{productId}")
    public ResponseEntity<Long> getStabilitiesMatrixIdByProductId(@PathVariable Long productId) {
        Optional<StabilitiesMatrix> stabilitiesMatrix = stabilitiesMatrixService.getStabilitiesMatrixByProductId(productId);
        return stabilitiesMatrix.map(matrix -> ResponseEntity.ok(matrix.getId()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}