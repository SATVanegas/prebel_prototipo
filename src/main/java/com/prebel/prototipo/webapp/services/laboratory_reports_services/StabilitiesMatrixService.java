package com.prebel.prototipo.webapp.services.laboratory_reports_services;

import com.prebel.prototipo.webapp.dtos.validations.laboratory_reports_requests.StabilitiesMatrixDTO;
import com.prebel.prototipo.webapp.models.laboratory_reports.Product;
import com.prebel.prototipo.webapp.models.laboratory_reports.StabilitiesMatrix;
import com.prebel.prototipo.webapp.models.role_module.User;
import com.prebel.prototipo.webapp.repositories.laboratory_reports_repositories.StabilitiesMatrixRepository;
import com.prebel.prototipo.webapp.services.role_module_services.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class StabilitiesMatrixService {

    private final StabilitiesMatrixRepository stabilitiesMatrixRepository;
    private final ProductService productService;
    private final UserService userService;

    public StabilitiesMatrixService(StabilitiesMatrixRepository stabilitiesMatrixRepository, ProductService productService, UserService userService) {
        this.stabilitiesMatrixRepository = stabilitiesMatrixRepository;
        this.productService = productService;
        this.userService = userService;
    }

    public Optional<StabilitiesMatrix> getStabilitiesMatrixById(Long id) {
        return stabilitiesMatrixRepository.findById(id);
    }

    public void createStabilitiesMatrix(@Valid StabilitiesMatrixDTO dto) {
        Product product = productService.getProductById(dto.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("El producto con ID " + dto.getProductId() + " no existe"));

        User customer = userService.getUserById(dto.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("El cliente con ID " + dto.getCustomerId() + " no existe"));

        User chemical = userService.getUserById(dto.getChemicalId())
                .orElseThrow(() -> new EntityNotFoundException("El químico con ID " + dto.getChemicalId() + " no existe"));

        User engineer = userService.getUserById(dto.getEngineerId())
                .orElseThrow(() -> new EntityNotFoundException("El ingeniero con ID " + dto.getEngineerId() + " no existe"));

        StabilitiesMatrix stabilitiesMatrix = new StabilitiesMatrix(dto, product, customer, chemical, engineer);
        stabilitiesMatrixRepository.save(stabilitiesMatrix);
    }

    public List<StabilitiesMatrix> getInspectionsDueInNext7Days() {
        Calendar calendar = Calendar.getInstance();
        Date startDate = calendar.getTime();

        calendar.add(Calendar.DAY_OF_YEAR, 7);
        Date endDate = calendar.getTime();

        return stabilitiesMatrixRepository.findInspectionsWithinDateRange(startDate, endDate);
    }
}
