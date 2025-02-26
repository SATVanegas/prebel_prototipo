package com.prebel.prototipo.webapp.services.laboratory_reports_services.test_services;

import com.prebel.prototipo.webapp.dtos.validations.laboratory_reports_requests.test_request.TestTemperatureDTO;
import com.prebel.prototipo.webapp.models.laboratory_reports.tests.Temperature;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TemperatureService {
    public void createTemperature(@Valid TestTemperatureDTO dto) {
    }

    public Optional<Temperature> getTemperature(Long id) {
        return Optional.empty();
    }
}
