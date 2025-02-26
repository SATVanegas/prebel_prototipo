package com.prebel.prototipo.webapp.dtos.validations.laboratory_reports_requests;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TestStorageDTO {

    @NotNull(message = "La temperatura maxima es obligatorio")
    private int maxTemperature; //In Celcius

    @NotNull(message = "La temperatura minima es obligatorio")
    private int minTemperature; //In Celcius

    @NotNull(message = "El codigo del equipo es obligatorio")
    private String equipmentCode;
    private String description;
}
