package com.prebel.prototipo.webapp.services.laboratory_reports_services;

import com.prebel.prototipo.webapp.dtos.validations.laboratory_reports_requests.ProductDTO;
import com.prebel.prototipo.webapp.models.laboratory_reports.Product;
import com.prebel.prototipo.webapp.models.role_module.User;
import com.prebel.prototipo.webapp.repositories.laboratory_reports_repositories.ProductRepository;
import com.prebel.prototipo.webapp.repositories.laboratory_reports_repositories.test_repositories.TestRepository;
import com.prebel.prototipo.webapp.services.role_module_services.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final UserService userService;

    public ProductService(ProductRepository productRepository, UserService userService) {
        this.productRepository = productRepository;
        this.userService = userService;
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public void createProduct(@Valid ProductDTO dto) {
        User customer = userService.getUserById(dto.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("El cliente con ID " + dto.getCustomerId() + " no existe"));

        User responsibleChemist = userService.getUserById(dto.getResponsibleChemistId())
                .orElseThrow(() -> new EntityNotFoundException("El químico responsable con ID " + dto.getResponsibleChemistId() + " no existe"));

        User responsibleEngineer = userService.getUserById(dto.getResponsibleEngineerId())
                .orElseThrow(() -> new EntityNotFoundException("El ingeniero responsable con ID " + dto.getResponsibleEngineerId() + " no existe"));

        User responsibleAnalyst = userService.getUserById(dto.getResponsibleAnalystId())
                .orElseThrow(() -> new EntityNotFoundException("El analista responsable con ID " + dto.getResponsibleAnalystId() + " no existe"));

        User technicianInCharge = userService.getUserById(dto.getTechnicianInChargeId())
                .orElseThrow(() -> new EntityNotFoundException("El técnico encargado con ID " + dto.getTechnicianInChargeId() + " no existe"));

        Product product = new Product(dto, customer, responsibleChemist, responsibleEngineer, responsibleAnalyst, technicianInCharge);
        productRepository.save(product);
    }
}

