package com.prebel.prototipo.webapp.services.laboratory_reports_services;

import com.prebel.prototipo.webapp.dtos.validations.laboratory_reports_requests.StabilitiesMatrixDTO;
import com.prebel.prototipo.webapp.models.laboratory_reports.Inspection;
import com.prebel.prototipo.webapp.models.laboratory_reports.Product;
import com.prebel.prototipo.webapp.models.laboratory_reports.StabilitiesMatrix;
import com.prebel.prototipo.webapp.repositories.laboratory_reports_repositories.InspectionRepository;
import com.prebel.prototipo.webapp.repositories.laboratory_reports_repositories.StabilitiesMatrixRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StabilitiesMatrixService {

    private final StabilitiesMatrixRepository stabilitiesMatrixRepository;
    private final InspectionRepository inspectionRepository;
    private final ProductService productService;

    public StabilitiesMatrixService(StabilitiesMatrixRepository stabilitiesMatrixRepository, InspectionRepository inspectionRepository, ProductService productService) {
        this.stabilitiesMatrixRepository = stabilitiesMatrixRepository;
        this.inspectionRepository = inspectionRepository;
        this.productService = productService;
    }

    public Optional<StabilitiesMatrixDTO> getStabilitiesMatrixDTOById(Long id) {
        return stabilitiesMatrixRepository.findById(id)
                .map(this::convertToDTO);
    }

    public Optional<StabilitiesMatrix> getStabilitiesMatrixById(Long id) {
        return stabilitiesMatrixRepository.findById(id);
    }

    private StabilitiesMatrixDTO convertToDTO(StabilitiesMatrix stabilitiesMatrix) {
        return new StabilitiesMatrixDTO(
                stabilitiesMatrix.getProjectCode(),
                stabilitiesMatrix.getFormulaCode(),
                stabilitiesMatrix.getPtReference(),
                stabilitiesMatrix.getBulkReference(),
                stabilitiesMatrix.getProduct().getId(),
                stabilitiesMatrix.getStudyDescription(),
                stabilitiesMatrix.getLocationEnvironment(),
                stabilitiesMatrix.getLocationOven(),
                stabilitiesMatrix.getLocationFridge(),
                stabilitiesMatrix.getLocationPhotolysisChamber(),
                stabilitiesMatrix.getInspectionIds(),
                stabilitiesMatrix.getBatch(),
                stabilitiesMatrix.getContainer(),
                stabilitiesMatrix.getDosage(),
                stabilitiesMatrix.getPackagingMaterial(),
                stabilitiesMatrix.getContainerColor(),
                stabilitiesMatrix.getCoverMaterial(),
                stabilitiesMatrix.getCoverColor(),
                stabilitiesMatrix.getSupplier(),
                stabilitiesMatrix.getCategory(),
                stabilitiesMatrix.getCosmeticForm(),
                stabilitiesMatrix.getStudyJustification(),
                stabilitiesMatrix.getStudyType(),
                stabilitiesMatrix.getDeliveryDatePt(),
                stabilitiesMatrix.getDateStartFormat(),
                stabilitiesMatrix.getStartDate(),
                stabilitiesMatrix.getStartMonth(),
                stabilitiesMatrix.getStartYear(),
                stabilitiesMatrix.getEndDate(),
                stabilitiesMatrix.getEndMonth(),
                stabilitiesMatrix.getEndYear(),
                stabilitiesMatrix.getQualification(),
                stabilitiesMatrix.getValidity(),
                stabilitiesMatrix.getJustificationRating()
        );
    }

    public StabilitiesMatrix createStabilitiesMatrix(@Valid StabilitiesMatrixDTO dto) {
        Product product = productService.getProductById(dto.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("El producto con ID " + dto.getProductId() + " no existe"));

        StabilitiesMatrix stabilitiesMatrix = new StabilitiesMatrix(dto, product);
        return stabilitiesMatrixRepository.save(stabilitiesMatrix);
    }

    public Optional<StabilitiesMatrix> getStabilitiesMatrixByProductId(Long productId) {
        List<StabilitiesMatrix> matrices = stabilitiesMatrixRepository.findByProductId(productId);
        if (matrices.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(matrices.get(0));
    }

    public List<StabilitiesMatrixDTO> getAllStabilitiesMatrixDTOs() {
        List<StabilitiesMatrix> stabilitiesMatrices = (List<StabilitiesMatrix>) stabilitiesMatrixRepository.findAll();
        return stabilitiesMatrices.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

}
