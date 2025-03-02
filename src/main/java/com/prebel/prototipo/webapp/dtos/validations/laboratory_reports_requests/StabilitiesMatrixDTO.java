package com.prebel.prototipo.webapp.dtos.validations.laboratory_reports_requests;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StabilitiesMatrixDTO {

    @NotNull(message = "El código de proyecto (projectCode) es obligatorio")
    @Size(min = 1, message = "El código de proyecto no puede estar vacío")
    private String projectCode;

    @NotNull(message = "El código de fórmula (formulaCode) es obligatorio")
    @Size(min = 1, message = "El código de fórmula no puede estar vacío")
    private String formulaCode;

    private String ptReference;
    private String bulkReference;

    @NotNull(message = "El ID del producto (productId) es obligatorio")
    private Long productId;

    private String studyDescription;

    @Min(value = 0, message = "La ubicación en ambiente debe ser un número positivo")
    private Integer locationEnvironment;

    @Min(value = 0, message = "La ubicación en horno debe ser un número positivo")
    private Integer locationOven;

    @Min(value = 0, message = "La ubicación en nevera debe ser un número positivo")
    private Integer locationFridge;

    @Min(value = 0, message = "La ubicación en cámara de fotólisis debe ser un número positivo")
    private Integer locationPhotolysisChamber;

    private List<Long> inspectionIds;

    private String batch;
    private String container;
    private String dosage;
    private String packagingMaterial;
    private String containerColor;
    private String coverMaterial;
    private String coverColor;
    private String supplier;

<<<<<<< Updated upstream
<<<<<<< Updated upstream
    @NotNull(message = "El ID del cliente (customerId) es obligatorio")
    private Long customerId;

=======
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
    private String category;
    private String cosmeticForm;
    private String studyJustification;
    private String studyType;

    private Date deliveryDatePt;
    private Date dateStartFormat;
    private Date startDate;

    @Min(value = 1, message = "El mes de inicio debe ser un número positivo")
    @Max(value = 12, message = "El mes de inicio debe estar entre 1 y 12")
    private Integer startMonth;

    @Min(value = 1900, message = "El año de inicio debe ser válido")
    private Integer startYear;

    private Date endDate;

    @Min(value = 1, message = "El mes de finalización debe ser un número positivo")
    @Max(value = 12, message = "El mes de finalización debe estar entre 1 y 12")
    private Integer endMonth;

    @Min(value = 1900, message = "El año de finalización debe ser válido")
    private Integer endYear;

    private String qualification;

    @Min(value = 0, message = "La validez debe ser un número positivo")
    private Integer validity;

    private String justificationRating;

<<<<<<< Updated upstream
<<<<<<< Updated upstream
    @NotNull(message = "El ID del químico (chemicalId) es obligatorio")
    private Long chemicalId;

    @NotNull(message = "El ID del ingeniero (engineerId) es obligatorio")
    private Long engineerId;
=======
    public StabilitiesMatrixDTO(String projectCode, String formulaCode, String ptReference, String bulkReference, Long id, String studyDescription, Integer locationEnvironment, Integer locationOven, Integer locationFridge, Integer locationPhotolysisChamber, List<Long> inspectionIds, String batch, String container, String dosage, String packagingMaterial, String containerColor, String coverMaterial, String coverColor, String supplier, String category, String cosmeticForm, String studyJustification, String studyType, Date deliveryDatePt, Date dateStartFormat, Date startDate, Integer startMonth, Integer startYear, Date endDate, Integer endMonth, Integer endYear, String qualification, Integer validity, String justificationRating, List<Long> inspectionIds1) {
    }
>>>>>>> Stashed changes
=======
    public StabilitiesMatrixDTO(String projectCode, String formulaCode, String ptReference, String bulkReference, Long id, String studyDescription, Integer locationEnvironment, Integer locationOven, Integer locationFridge, Integer locationPhotolysisChamber, List<Long> inspectionIds, String batch, String container, String dosage, String packagingMaterial, String containerColor, String coverMaterial, String coverColor, String supplier, String category, String cosmeticForm, String studyJustification, String studyType, Date deliveryDatePt, Date dateStartFormat, Date startDate, Integer startMonth, Integer startYear, Date endDate, Integer endMonth, Integer endYear, String qualification, Integer validity, String justificationRating, List<Long> inspectionIds1) {
    }
>>>>>>> Stashed changes
}
