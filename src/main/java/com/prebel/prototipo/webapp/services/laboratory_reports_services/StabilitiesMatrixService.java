package com.prebel.prototipo.webapp.services.laboratory_reports_services;

import com.prebel.prototipo.webapp.dtos.validations.laboratory_reports_requests.StabilitiesMatrixDTO;
import com.prebel.prototipo.webapp.models.laboratory_reports.Product;
import com.prebel.prototipo.webapp.models.laboratory_reports.StabilitiesMatrix;
import com.prebel.prototipo.webapp.repositories.laboratory_reports_repositories.StabilitiesMatrixRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StabilitiesMatrixService {

    private final StabilitiesMatrixRepository stabilitiesMatrixRepository;
    private final ProductService productService;

    public StabilitiesMatrixService(StabilitiesMatrixRepository stabilitiesMatrixRepository, ProductService productService) {
        this.stabilitiesMatrixRepository = stabilitiesMatrixRepository;
        this.productService = productService;
    }

    public Optional<StabilitiesMatrix> getStabilitiesMatrixById(Long id) {
        return stabilitiesMatrixRepository.findById(id);
    }

    public StabilitiesMatrix createStabilitiesMatrix(@Valid StabilitiesMatrixDTO dto) {
        Product product = productService.getProductById(dto.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("El producto con ID " + dto.getProductId() + " no existe"));

        StabilitiesMatrix stabilitiesMatrix = new StabilitiesMatrix(dto, product);
        return stabilitiesMatrixRepository.save(stabilitiesMatrix);
    }

    public Optional<StabilitiesMatrix> getStabilitiesMatrixByProductId(Long productId) {
        return stabilitiesMatrixRepository.findByProductId(productId);
    }

}
