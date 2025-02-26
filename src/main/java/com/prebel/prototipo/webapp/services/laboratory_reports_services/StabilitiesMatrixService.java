package com.prebel.prototipo.webapp.services.laboratory_reports_services;

import com.prebel.prototipo.webapp.dtos.validations.laboratory_reports_requests.StabilitiesMatrixDTO;
import com.prebel.prototipo.webapp.models.laboratory_reports.StabilitiesMatrix;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StabilitiesMatrixService {
    public void createStabilitiesMatrix(@Valid StabilitiesMatrixDTO dto) {
    }

    public Optional<StabilitiesMatrix> getStabilitiesMatrix(Long id) {

        return Optional.empty();
    }
}
