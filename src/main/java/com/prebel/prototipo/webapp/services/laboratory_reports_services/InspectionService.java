package com.prebel.prototipo.webapp.services.laboratory_reports_services;

import com.prebel.prototipo.webapp.dtos.validations.laboratory_reports_requests.InspectionDTO;
import com.prebel.prototipo.webapp.dtos.validations.laboratory_reports_requests.StabilitiesMatrixDTO;
import com.prebel.prototipo.webapp.models.laboratory_reports.Inspection;
import com.prebel.prototipo.webapp.models.laboratory_reports.StabilitiesMatrix;
import com.prebel.prototipo.webapp.models.laboratory_reports.tests.Test;
import com.prebel.prototipo.webapp.repositories.laboratory_reports_repositories.InspectionRepository;
import com.prebel.prototipo.webapp.services.laboratory_reports_services.test_services.TestService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public InspectionDTO getInspectionById(Long id) {
        Inspection inspection = inspectionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Inspection not found with id " + id));
        return convertToDTO(inspection);
    }

    public void createInspection(@Valid InspectionDTO dto) {
        StabilitiesMatrixDTO stabilitiesMatrix = stabilitiesMatrixService.getStabilitiesMatrixById(dto.getStabilitiesMatrixId())
                .orElseThrow(() -> new EntityNotFoundException("La matriz de estabilidad con ID " + dto.getStabilitiesMatrixId() + " no existe"));

        Test test = testService.getTestById(dto.getTestId())
                .orElseThrow(() -> new EntityNotFoundException("El test con ID " + dto.getTestId() + " no existe"));

        Inspection inspection = new Inspection(dto, stabilitiesMatrix, test);
        inspectionRepository.save(inspection);
    }

    public Optional<Inspection> getLastInspection() {
        return inspectionRepository.findTopByOrderByIdDesc();
    }

    public List<InspectionDTO> getAllInspectionDTOs() {
        List<Inspection> inspections = (List<Inspection>) inspectionRepository.findAll();
        return inspections.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private InspectionDTO convertToDTO(Inspection inspection) {
        return new InspectionDTO(
                inspection.getExpectedDate(),
                inspection.getRealDate(),
                inspection.getResponseTime(),
                inspection.getAerosolStove(),
                inspection.getInOut(),
                inspection.getStove(),
                inspection.getHrStove(),
                inspection.getEnvironment(),
                inspection.getFridge(),
                inspection.getPhotolysis(),
                inspection.getStabilitiesMatrix().getId(),
                inspection.getTest().getId()
        );
    }
}