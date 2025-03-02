package com.prebel.prototipo.webapp.controllers.laboratory_reports_controllers;

import com.prebel.prototipo.webapp.dtos.validations.laboratory_reports_requests.InspectionDTO;
import com.prebel.prototipo.webapp.models.laboratory_reports.Inspection;
import com.prebel.prototipo.webapp.services.laboratory_reports_services.InspectionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/inspections")
public class InspectionController {

    private final InspectionService inspectionService;

    public InspectionController(InspectionService inspectionService) { this.inspectionService = inspectionService;}

    // Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Inspection> getInspectionById(@PathVariable Long id) {
        Optional<Inspection> inspection = inspectionService.getInspectionById(id);
        return inspection.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear una nueva Inspection
    @PostMapping
    public ResponseEntity<String> createInspection(@Valid @RequestBody InspectionDTO dto) {
        inspectionService.createInspection(dto);
        return ResponseEntity.ok("Inspection creada correctamente");
    }

    @GetMapping("/last")
    public ResponseEntity<?> getLastInspection() {
        Optional<Inspection> lastInspection = inspectionService.getLastInspection();
        return lastInspection.map(ResponseEntity::ok)
                .orElseGet(() -> (ResponseEntity<Inspection>) ResponseEntity.ok());
    }
}

