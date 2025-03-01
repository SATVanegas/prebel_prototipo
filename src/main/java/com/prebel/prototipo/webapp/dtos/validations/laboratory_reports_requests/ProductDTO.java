package com.prebel.prototipo.webapp.dtos.validations.laboratory_reports_requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private String productDescription;

    @NotNull(message = "La referencia es obligatoria")
    @Size(max = 100, message = "La referencia no debe superar los 100 caracteres")
    private String reference;

    @NotNull(message = "El lote (batch) es obligatorio")
    @Size(max = 50, message = "El lote no debe superar los 50 caracteres")
    private String batch;

    @NotNull(message = "El tipo de empaque es obligatorio")
    @Size(max = 100, message = "El tipo de empaque no debe superar los 100 caracteres")
    private String packagingType;

    @NotNull(message = "El material del empaque es obligatorio")
    @Size(max = 100, message = "El material del empaque no debe superar los 100 caracteres")
    private String packagingMaterial;

    private String containerColor;
    private String lidMaterial;
    private String lidColor;
    private String formulaNumber;
    private String projectCode;
    private String projectName;

    private String brand;
    private String studyType;

    @Min(value = 1, message = "El consecutivo debe ser mayor a 0")
    private int consecutive;

    private String justification;
    private String qualification;
    private String establishedValidity;

    @Min(value = 1, message = "La duración del estudio debe ser mayor a 0")
    private int studyDuration;

    @PastOrPresent(message = "La fecha de inicio no puede ser en el futuro")
    private Date startDate;

    @PastOrPresent(message = "La fecha de finalización no puede ser en el futuro")
    private Date finishDate;
}