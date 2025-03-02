package com.prebel.prototipo.webapp.dtos.validations.laboratory_reports_requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InspectionDTO {

    @NotNull(message = "La fecha esperada (expectedDate) es obligatoria")
    private Date expectedDate;

    private Date realDate;

    @Min(value = 0, message = "El tiempo de respuesta debe ser un número positivo")
    private int responseTime;

    private int aerosolStove;
    private int inOut;
    private int stove;
    private int hrStove;
    private int environment;
    private int fridge;
    private int photolysis;

    @NotNull(message = "La matriz de estabilidad (stabilitiesMatrixId) es obligatoria")
    private Long stabilitiesMatrixId;

    @NotNull(message = "El ID de la prueba (testId) es obligatorio")
    private Long testId;
}
