package com.prebel.prototipo.webapp.dtos.validations.laboratory_reports_requests.test_request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestTemperatureDTO {

    @NotNull(message = "La unidad (unit) es obligatoria")
    @Size(min = 1, message = "La unidad no puede estar vacía")
    private String unit;

    @NotNull(message = "El tiempo (time) es obligatorio")
    @Min(value = 0, message = "El tiempo en semanas debe ser un número positivo")
    private Integer time;// In weeks

    @NotNull(message = "El equipo (equipment) es obligatorio")
    private Integer equipment;
}
