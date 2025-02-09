package com.prebel.prototipo.webapp.dtos.validations;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TestsDTO {

    @NotNull(message = "La unidad (unit) es obligatoria")
    @Size(min = 1, message = "La unidad no puede estar vacía")
    private String unit;

    @NotNull(message = "El tiempo (time) es obligatorio")
    @Min(value = 0, message = "El tiempo en semanas debe ser un número positivo")
    private int time;// In weeks

    @NotNull(message = "El equipo (equipment) es obligatorio")
    private int equipment;

    @NotNull(message = "El método (method) es obligatorio")
    private String method;

    private String specification;
    private String initialResultsDevelopmentLaboratory;
    private String initialResultsStabilityLaboratory;

}