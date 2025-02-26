package com.prebel.prototipo.webapp.dtos.validations.laboratory_reports_requests;

import com.prebel.prototipo.webapp.models.laboratory_reports.EnumTest;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestConditionDTO {

    @NotNull(message = "El tipo (type) es obligatorio")
    private EnumTest type;

    @NotNull(message = "La unidad (unit) es obligatoria")
    @Size(min = 1, message = "La unidad no puede estar vacía")
    private String unit;

    @NotNull(message = "El tiempo (time) es obligatorio")
    @Min(value = 0, message = "El tiempo en semanas debe ser un número positivo")
    private int time; // In weeks

    @NotNull(message = "El equipo (equipment) es obligatorio")
    private int equipment;

    @NotNull(message = "El método (method) es obligatorio")
    private String method;

    private String specification;
    private String initialResultsDevelopmentLaboratory;
    private String initialResultsStabilityLaboratory;

    private Long testId;
}
