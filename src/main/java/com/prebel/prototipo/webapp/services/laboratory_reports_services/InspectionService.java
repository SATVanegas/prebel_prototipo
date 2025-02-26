package com.prebel.prototipo.webapp.services.laboratory_reports_services;

import com.prebel.prototipo.webapp.dtos.validations.laboratory_reports_requests.InspectionDTO;
import com.prebel.prototipo.webapp.models.laboratory_reports.Inspection;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InspectionService {
    public void createInspection(@Valid InspectionDTO dto) {
    }

    public Optional<Inspection> getInspection(Long id) {

        return Optional.empty();
    }
}