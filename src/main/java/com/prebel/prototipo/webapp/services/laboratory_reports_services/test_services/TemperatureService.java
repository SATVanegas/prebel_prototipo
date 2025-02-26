package com.prebel.prototipo.webapp.services.laboratory_reports_services.test_services;

import com.prebel.prototipo.webapp.dtos.validations.laboratory_reports_requests.test_request.TestTemperatureDTO;
import com.prebel.prototipo.webapp.models.laboratory_reports.tests.Temperature;
import com.prebel.prototipo.webapp.models.laboratory_reports.tests.Test;
import com.prebel.prototipo.webapp.repositories.laboratory_reports_repositories.test_repositories.TemperatureRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TemperatureService {

    private final TemperatureRepository temperatureRepository;
    private final TestService testService;

    public TemperatureService(TemperatureRepository temperatureRepository, TestService testService) {
        this.temperatureRepository = temperatureRepository;
        this.testService = testService;
    }

    public Optional<Temperature> getTemperatureById(Long id) {
        return temperatureRepository.findById(id);
    }

    public void createTemperature(@Valid TestTemperatureDTO dto) {
        Test test = testService.getTestById(dto.getTestId())
                .orElseThrow(() -> new EntityNotFoundException("El test con ID " + dto.getTestId() + " no existe"));
        Temperature temperature = new Temperature(dto, test);
        temperatureRepository.save(temperature);
    }

}
