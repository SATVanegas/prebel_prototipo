package com.prebel.prototipo.webapp.controllers.laboratory_reports_controllers;

import com.prebel.prototipo.webapp.models.laboratory_reports.Inspection;
import com.prebel.prototipo.webapp.models.StabilitiesMatrix;
import com.prebel.prototipo.webapp.models.laboratory_reports.Test;
import com.prebel.prototipo.webapp.repositories.laboratory_reports_repositories.InspectionRepository;
import com.prebel.prototipo.webapp.repositories.laboratory_reports_repositories.StabilitiesMatrixRepository;
import com.prebel.prototipo.webapp.repositories.laboratory_reports_repositories.TestRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/inspections")
public class InspectionController {

    private final InspectionRepository inspectionRepository;
    private final StabilitiesMatrixRepository stabilitiesMatrixRepository;
    private final TestRepository testRepository;

    public InspectionController(InspectionRepository inspectionRepository,
                                StabilitiesMatrixRepository stabilitiesMatrixRepository,
                                TestRepository testRepository) {
        this.inspectionRepository = inspectionRepository;
        this.stabilitiesMatrixRepository = stabilitiesMatrixRepository;
        this.testRepository = testRepository;
    }

    @PostMapping
    public ResponseEntity<?> createInspection(@RequestBody InspectionRequest request) {
        Optional<StabilitiesMatrix> stabilitiesMatrixOpt = stabilitiesMatrixRepository.findById(request.getStabilitiesMatrixId());
        Optional<Test> testOpt = testRepository.findById(request.getTestId());

        if (stabilitiesMatrixOpt.isEmpty() || testOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("StabilitiesMatrix or Test not found");
        }

        Inspection inspection = new Inspection();
        inspection.setExpectedDate(request.getExpectedDate());
        inspection.setRealDate(request.getRealDate());
        inspection.setResponseTime(request.getResponseTime());
        inspection.setAerosolStove(request.getAerosolStove());
        inspection.setInOut(request.getInOut());
        inspection.setStove(request.getStove());
        inspection.setHrStove(request.getHrStove());
        inspection.setEnvironment(request.getEnvironment());
        inspection.setFridge(request.getFridge());
        inspection.setPhotolysis(request.getPhotolysis());
        inspection.setStabilitiesMatrix(stabilitiesMatrixOpt.get());
        inspection.setTest(testOpt.get());

        Inspection savedInspection = inspectionRepository.save(inspection);
        return ResponseEntity.ok(savedInspection);
    }

    @Setter
    @Getter
    public static class InspectionRequest {
        // Getters y Setters
        private Long stabilitiesMatrixId;
        private Long testId;
        private java.util.Date expectedDate;
        private java.util.Date realDate;
        private int responseTime;
        private int aerosolStove;
        private int inOut;
        private int stove;
        private int hrStove;
        private int environment;
        private int fridge;
        private int photolysis;

    }
}

