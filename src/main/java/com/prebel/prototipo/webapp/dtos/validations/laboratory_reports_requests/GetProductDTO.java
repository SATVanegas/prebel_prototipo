package com.prebel.prototipo.webapp.dtos.validations.laboratory_reports_requests;

import com.prebel.prototipo.webapp.models.laboratory_reports.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetProductDTO {
    private Long id;
    private String productDescription;
    private String brand;
    private Long stabilitiesMatrixId;

    public GetProductDTO(Product product, Long stabilitiesMatrixId) {
    }
}