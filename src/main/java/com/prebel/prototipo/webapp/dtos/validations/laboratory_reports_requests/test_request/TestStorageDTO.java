package com.prebel.prototipo.webapp.dtos.validations.laboratory_reports_requests.test_request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestStorageDTO {

    @NotNull(message = "La temperatura maxima es obligatorio")
    private Integer  maxTemperature; //In Celcius

    @NotNull(message = "La temperatura minima es obligatorio")
    private Integer  minTemperature; //In Celcius

    @NotNull(message = "El codigo del equipo es obligatorio")
    private String equipmentCode;
    private String description;
}
