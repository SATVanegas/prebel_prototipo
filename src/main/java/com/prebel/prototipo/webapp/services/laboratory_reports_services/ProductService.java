package com.prebel.prototipo.webapp.services.laboratory_reports_services;

import com.prebel.prototipo.webapp.dtos.validations.laboratory_reports_requests.ProductDTO;
import com.prebel.prototipo.webapp.models.laboratory_reports.Product;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {
    public void createProduct(@Valid ProductDTO dto) {
    }

    public Optional<Product> getProduct(Long id) {

        return Optional.empty();
    }
}
