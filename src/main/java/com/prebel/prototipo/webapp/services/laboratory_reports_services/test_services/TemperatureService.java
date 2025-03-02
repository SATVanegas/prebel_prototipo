package com.prebel.prototipo.webapp.services.laboratory_reports_services.test_services;

import com.prebel.prototipo.webapp.dtos.validations.laboratory_reports_requests.test_request.TestTemperatureDTO;
import com.prebel.prototipo.webapp.models.laboratory_reports.tests.Temperature;
import com.prebel.prototipo.webapp.repositories.laboratory_reports_repositories.test_repositories.TemperatureRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TemperatureService {

    private final TemperatureRepository temperatureRepository;

    public TemperatureService( TemperatureRepository temperatureRepository) {
        this.temperatureRepository = temperatureRepository;
    }

    public Optional<Temperature> getTemperatureById(Long id) {
        return temperatureRepository.findById(id);
    }

    public void createTemperature(@Valid TestTemperatureDTO dto) {
        Temperature temperature = new Temperature(dto);
        temperatureRepository.save(temperature);
    }

}
