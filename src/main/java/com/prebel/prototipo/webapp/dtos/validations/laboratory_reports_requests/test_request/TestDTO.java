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
public class TestDTO {

    @NotNull(message = "El producto (product) es obligatorio")
    private Long productId;

    @NotNull(message = "La temperatura (temperature) es obligatoria")
    private Long temperatureId;

    @NotNull(message = "El color (color) es obligatorio")
    private Long colorId;

    @NotNull(message = "El olor (odor) es obligatorio")
    private Long odorId;

    @NotNull(message = "La apariencia (appearance) es obligatoria")
    private Long appearanceId;

    @NotNull(message = "El pH (ph) es obligatorio")
    private Long phId;

    @NotNull(message = "La viscosidad (viscosity) es obligatoria")
    private Long viscosityId;

    @NotNull(message = "La gravedad específica (specific gravity) es obligatoria")
    private Long specificGravityId;

    @NotNull(message = "El recuento total de bacterias (total bacteria count) es obligatorio")
    private Long totalBacteriaCountId;

    @NotNull(message = "El recuento de hongos y levaduras (fungi yeast count) es obligatorio")
    private Long fungiYeastCountId;

    @NotNull(message = "Los patógenos (pathogens) son obligatorios")
    private Long pathogensId;

    @NotNull(message = "El almacenamiento (storage) es obligatorio")
    private Long storageId;

    private Long userOrganolepticTestsId;

    private Long userPhysicochemicalTestsId;

    private String observations;
    private String conclusion;

}