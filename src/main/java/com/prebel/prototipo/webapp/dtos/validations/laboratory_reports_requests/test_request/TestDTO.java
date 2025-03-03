package com.prebel.prototipo.webapp.dtos.validations.laboratory_reports_requests.test_request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestDTO {

    @NotNull(message = "El producto (product) es obligatorio")
    private Long productId;

    @NotNull(message = "La temperatura (temperature) es obligatoria")
    private Long temperatureId;

    @NotNull(message = "El almacenamiento (storage) es obligatorio")
    private Long storageId;

    private String observations;
    private String conclusion;

}