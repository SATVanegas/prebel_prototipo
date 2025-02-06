package com.prebel.prototipo.webapp.controllers.laboratory_reports_controllers;

import com.prebel.prototipo.webapp.models.StabilitiesMatrix;
import com.prebel.prototipo.webapp.models.laboratory_reports.Product;
import com.prebel.prototipo.webapp.models.role_module.User;
import com.prebel.prototipo.webapp.repositories.laboratory_reports_repositories.ProductRepository;
import com.prebel.prototipo.webapp.repositories.laboratory_reports_repositories.StabilitiesMatrixRepository;
import com.prebel.prototipo.webapp.repositories.role_module_repositories.UserRepository;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/stabilities-matrix")
public class StabilitiesMatrixController {

    private final StabilitiesMatrixRepository stabilitiesMatrixRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public StabilitiesMatrixController(StabilitiesMatrixRepository stabilitiesMatrixRepository,
                                       ProductRepository productRepository,
                                       UserRepository userRepository) {
        this.stabilitiesMatrixRepository = stabilitiesMatrixRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<?> createStabilitiesMatrix(@Valid @RequestBody StabilitiesMatrixRequest request) {
        Optional<Product> productOpt = productRepository.findById(request.getProductId());
        Optional<User> customerOpt = userRepository.findById(request.getCustomerId());
        Optional<User> chemicalOpt = userRepository.findById(request.getChemicalId());
        Optional<User> engineerOpt = userRepository.findById(request.getEngineerId());

        if (productOpt.isEmpty() || customerOpt.isEmpty() || chemicalOpt.isEmpty() || engineerOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Error: Alguno de los IDs proporcionados no existe.");
        }

        StabilitiesMatrix matrix = new StabilitiesMatrix();
        matrix.setProduct(productOpt.get());
        matrix.setCustomer(customerOpt.get());
        matrix.setChemical(chemicalOpt.get());
        matrix.setEngineer(engineerOpt.get());
        matrix.setProjectCode(request.getProjectCode());
        matrix.setFormulaCode(request.getFormulaCode());
        matrix.setPtReference(request.getPtReference());
        matrix.setBulkReference(request.getBulkReference());
        matrix.setStudyDescription(request.getStudyDescription());
        matrix.setLocationEnvironment(request.getLocationEnvironment());
        matrix.setLocationOven(request.getLocationOven());
        matrix.setLocationFridge(request.getLocationFridge());
        matrix.setLocationPhotolysisChamber(request.getLocationPhotolysisChamber());
        matrix.setBatch(request.getBatch());
        matrix.setContainer(request.getContainer());
        matrix.setDosage(request.getDosage());
        matrix.setPackagingMaterial(request.getPackagingMaterial());
        matrix.setContainerColor(request.getContainerColor());
        matrix.setCoverMaterial(request.getCoverMaterial());
        matrix.setCoverColor(request.getCoverColor());
        matrix.setSupplier(request.getSupplier());
        matrix.setCategory(request.getCategory());
        matrix.setCosmeticForm(request.getCosmeticForm());
        matrix.setStudyJustification(request.getStudyJustification());
        matrix.setStudyType(request.getStudyType());
        matrix.setStartMonth(request.getStartMonth());
        matrix.setStartYear(request.getStartYear());
        matrix.setEndMonth(request.getEndMonth());
        matrix.setEndYear(request.getEndYear());
        matrix.setQualification(request.getQualification());
        matrix.setValidity(request.getValidity());
        matrix.setJustificationRating(request.getJustificationRating());

        StabilitiesMatrix savedMatrix = stabilitiesMatrixRepository.save(matrix);
        return ResponseEntity.ok(savedMatrix);
    }

    @Getter
    @Setter
    public static class StabilitiesMatrixRequest {
        private Long productId;
        private Long customerId;
        private Long chemicalId;
        private Long engineerId;
        private String projectCode;
        private String formulaCode;
        private String ptReference;
        private String bulkReference;
        private String studyDescription;
        private Integer locationEnvironment;
        private Integer locationOven;
        private Integer locationFridge;
        private Integer locationPhotolysisChamber;
        private String batch;
        private String container;
        private String dosage;
        private String packagingMaterial;
        private String containerColor;
        private String coverMaterial;
        private String coverColor;
        private String supplier;
        private String category;
        private String cosmeticForm;
        private String studyJustification;
        private String studyType;
        private Integer startMonth;
        private Integer startYear;
        private Integer endMonth;
        private Integer endYear;
        private String qualification;
        private Integer validity;
        private String justificationRating;

        // Getters y Setters
        public Long getProductId() { return productId; }
        public void setProductId(Long productId) { this.productId = productId; }

        public Long getCustomerId() { return customerId; }
        public void setCustomerId(Long customerId) { this.customerId = customerId; }

        public Long getChemicalId() { return chemicalId; }
        public void setChemicalId(Long chemicalId) { this.chemicalId = chemicalId; }

        public Long getEngineerId() { return engineerId; }
        public void setEngineerId(Long engineerId) { this.engineerId = engineerId; }

        public String getProjectCode() { return projectCode; }
        public void setProjectCode(String projectCode) { this.projectCode = projectCode; }

        public String getFormulaCode() { return formulaCode; }
        public void setFormulaCode(String formulaCode) { this.formulaCode = formulaCode; }

        public String getPtReference() { return ptReference; }
        public void setPtReference(String ptReference) { this.ptReference = ptReference; }

        public String getBulkReference() { return bulkReference; }
        public void setBulkReference(String bulkReference) { this.bulkReference = bulkReference; }

        public String getStudyDescription() { return studyDescription; }
        public void setStudyDescription(String studyDescription) { this.studyDescription = studyDescription; }

        public Integer getLocationEnvironment() { return locationEnvironment; }
        public void setLocationEnvironment(Integer locationEnvironment) { this.locationEnvironment = locationEnvironment; }

        public Integer getLocationOven() { return locationOven; }
        public void setLocationOven(Integer locationOven) { this.locationOven = locationOven; }

        public Integer getLocationFridge() { return locationFridge; }
        public void setLocationFridge(Integer locationFridge) { this.locationFridge = locationFridge; }

        public Integer getLocationPhotolysisChamber() { return locationPhotolysisChamber; }
        public void setLocationPhotolysisChamber(Integer locationPhotolysisChamber) { this.locationPhotolysisChamber = locationPhotolysisChamber; }

        public String getStudyType() { return studyType; }
        public void setStudyType(String studyType) { this.studyType = studyType; }

        public Integer getStartMonth() { return startMonth; }
        public void setStartMonth(Integer startMonth) { this.startMonth = startMonth; }

        public Integer getStartYear() { return startYear; }
        public void setStartYear(Integer startYear) { this.startYear = startYear; }

        public Integer getEndMonth() { return endMonth; }
        public void setEndMonth(Integer endMonth) { this.endMonth = endMonth; }

        public Integer getEndYear() { return endYear; }
        public void setEndYear(Integer endYear) { this.endYear = endYear; }

        public String getQualification() { return qualification; }
        public void setQualification(String qualification) { this.qualification = qualification; }

        public Integer getValidity() { return validity; }
        public void setValidity(Integer validity) { this.validity = validity; }

        public String getJustificationRating() { return justificationRating; }
        public void setJustificationRating(String justificationRating) { this.justificationRating = justificationRating; }
    }
}

