package com.prebel.prototipo.webapp.services.laboratory_reports_services.test_services;

import com.prebel.prototipo.webapp.dtos.validations.laboratory_reports_requests.test_request.TestConditionDTO;
import com.prebel.prototipo.webapp.models.laboratory_reports.tests.Condition;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConditionService {
    public void createCondition(@Valid TestConditionDTO dto) {
    }

    public Optional<Condition> getCondition(Long id) {
        return Optional.empty();
    }
}
