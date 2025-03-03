package com.prebel.prototipo.webapp.models.laboratory_reports;

import java.util.Date;
import java.util.List;

import com.prebel.prototipo.webapp.dtos.validations.laboratory_reports_requests.GetProductDTO;
import com.prebel.prototipo.webapp.dtos.validations.laboratory_reports_requests.StabilitiesMatrixDTO;
import com.prebel.prototipo.webapp.models.role_module.User;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "stabilities_matrix")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StabilitiesMatrix {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String projectCode;
    private String formulaCode;
    private String ptReference;
    private String bulkReference;

    @OneToOne
    @JoinColumn(name = "product_id", unique = true)
    private Product product;

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
    private Date deliveryDatePt;
    private Date dateStartFormat;
    private Date startDate;
    private Integer startMonth;
    private Integer startYear;
    private Date endDate;
    private Integer endMonth;
    private Integer endYear;

    @ElementCollection
    @CollectionTable(name = "inspections", joinColumns = @JoinColumn(name = "stabilities_matrix_id"))
    @Column(name = "id")
    private List<Long> inspectionIds;

    private String qualification;
    private Integer validity;
    private String justificationRating;

    public StabilitiesMatrix(@Valid StabilitiesMatrixDTO dto, Product product) {
        this.projectCode = dto.getProjectCode();
        this.formulaCode = dto.getFormulaCode();
        this.ptReference = dto.getPtReference();
        this.bulkReference = dto.getBulkReference();
        this.product = product;
        this.studyDescription = dto.getStudyDescription();
        this.locationEnvironment = dto.getLocationEnvironment();
        this.locationOven = dto.getLocationOven();
        this.locationFridge = dto.getLocationFridge();
        this.locationPhotolysisChamber = dto.getLocationPhotolysisChamber();
        this.batch = dto.getBatch();
        this.container = dto.getContainer();
        this.dosage = dto.getDosage();
        this.packagingMaterial = dto.getPackagingMaterial();
        this.containerColor = dto.getContainerColor();
        this.coverMaterial = dto.getCoverMaterial();
        this.coverColor = dto.getCoverColor();
        this.supplier = dto.getSupplier();
        this.category = dto.getCategory();
        this.cosmeticForm = dto.getCosmeticForm();
        this.studyJustification = dto.getStudyJustification();
        this.studyType = dto.getStudyType();
        this.deliveryDatePt = dto.getDeliveryDatePt();
        this.dateStartFormat = dto.getDateStartFormat();
        this.startDate = dto.getStartDate();
        this.startMonth = dto.getStartMonth();
        this.startYear = dto.getStartYear();
        this.endDate = dto.getEndDate();
        this.endMonth = dto.getEndMonth();
        this.endYear = dto.getEndYear();
        this.qualification = dto.getQualification();
        this.validity = dto.getValidity();
        this.justificationRating = dto.getJustificationRating();
    }

    public StabilitiesMatrix(@Valid StabilitiesMatrixDTO dto, GetProductDTO product) {
    }
}