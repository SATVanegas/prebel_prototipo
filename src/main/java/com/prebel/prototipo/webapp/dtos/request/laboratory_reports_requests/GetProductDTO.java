package com.prebel.prototipo.webapp.dtos.request.laboratory_reports_requests;

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
}