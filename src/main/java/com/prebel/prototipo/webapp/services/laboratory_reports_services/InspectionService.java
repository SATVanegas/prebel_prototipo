package com.prebel.prototipo.webapp.services.laboratory_reports_services;

import com.prebel.prototipo.webapp.dtos.request.laboratory_reports_requests.InspectionDTO;
import com.prebel.prototipo.webapp.models.laboratory_reports.Inspection;
import com.prebel.prototipo.webapp.models.laboratory_reports.StabilitiesMatrix;
import com.prebel.prototipo.webapp.models.laboratory_reports.tests.Test;
import com.prebel.prototipo.webapp.repositories.laboratory_reports_repositories.InspectionRepository;
import com.prebel.prototipo.webapp.services.laboratory_reports_services.test_services.TestService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InspectionService {

    private final InspectionRepository inspectionRepository;
    private final StabilitiesMatrixService stabilitiesMatrixService;
    private final TestService testService;

    public InspectionService(InspectionRepository inspectionRepository,
                             StabilitiesMatrixService stabilitiesMatrixService,
                             TestService testService) {
        this.inspectionRepository = inspectionRepository;
        this.stabilitiesMatrixService = stabilitiesMatrixService;
        this.testService = testService;
    }

    public Optional<Inspection> getInspectionById(Long id) {
        return inspectionRepository.findById(id);
    }

    public void createInspection(@Valid InspectionDTO dto) {
        StabilitiesMatrix stabilitiesMatrix = stabilitiesMatrixService.getStabilitiesMatrixById(dto.getStabilitiesMatrixId())
                .orElseThrow(() -> new EntityNotFoundException("La matriz de estabilidad con ID " + dto.getStabilitiesMatrixId() + " no existe"));

        Test test = testService.getTestById(dto.getTestId())
                .orElseThrow(() -> new EntityNotFoundException("El test con ID " + dto.getTestId() + " no existe"));

        Inspection inspection = new Inspection(dto, stabilitiesMatrix, test);
        inspectionRepository.save(inspection);
    }

    public Optional<Inspection> getLastInspection() {
        return inspectionRepository.findTopByOrderByIdDesc();
    }

}
