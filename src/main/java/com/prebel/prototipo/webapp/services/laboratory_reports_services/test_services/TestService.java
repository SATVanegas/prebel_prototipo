package com.prebel.prototipo.webapp.services.laboratory_reports_services.test_services;

import com.prebel.prototipo.webapp.dtos.validations.laboratory_reports_requests.TestDTO;
import com.prebel.prototipo.webapp.models.laboratory_reports.Test;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TestService {
    public void createTest(@Valid TestDTO dto) {
    }

    public Optional<Test> getTest(Long id) {

        return Optional.empty();
    }
}

