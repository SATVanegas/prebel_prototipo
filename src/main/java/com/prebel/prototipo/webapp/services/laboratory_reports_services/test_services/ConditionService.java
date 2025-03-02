package com.prebel.prototipo.webapp.services.laboratory_reports_services.test_services;

import com.prebel.prototipo.webapp.dtos.validations.laboratory_reports_requests.test_request.TestConditionDTO;
import com.prebel.prototipo.webapp.dtos.validations.laboratory_reports_requests.test_request.TestDTO;
import com.prebel.prototipo.webapp.models.laboratory_reports.EnumTest;
import com.prebel.prototipo.webapp.models.laboratory_reports.tests.Condition;
import com.prebel.prototipo.webapp.repositories.laboratory_reports_repositories.test_repositories.ConditionRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ConditionService {

    private final ConditionRepository conditionRepository;
    public ConditionService(ConditionRepository conditionRepository) {
        this.conditionRepository = conditionRepository;
    }

    public Optional<Condition> getConditionById(Long id) {

        return conditionRepository.findById(id);
    }

    public Condition createCondition(@Valid TestConditionDTO dto) {
        Condition condition = new Condition(dto);
        conditionRepository.save(condition);
        return condition;
    }

    public List<Condition> getConditionsFromDTO(TestDTO dto) {
        List<Condition> conditions = new ArrayList<>();

        if (dto.getColorId() != null) {
            conditions.add(getValidCondition(dto.getColorId(), EnumTest.COLOR));
        }
        if (dto.getOdorId() != null) {
            conditions.add(getValidCondition(dto.getOdorId(), EnumTest.ODOR));
        }
        if (dto.getAppearanceId() != null) {
            conditions.add(getValidCondition(dto.getAppearanceId(), EnumTest.APPEARANCE));
        }
        if (dto.getPhId() != null) {
            conditions.add(getValidCondition(dto.getPhId(), EnumTest.PH));
        }
        if (dto.getViscosityId() != null) {
            conditions.add(getValidCondition(dto.getViscosityId(), EnumTest.VISCOSITY));
        }
        if (dto.getSpecificGravityId() != null) {
            conditions.add(getValidCondition(dto.getSpecificGravityId(), EnumTest.SPECIFIC_GRAVITY));
        }
        if (dto.getTotalBacteriaCountId() != null) {
            conditions.add(getValidCondition(dto.getTotalBacteriaCountId(), EnumTest.TOTAL_BACTERIA_COUNT));
        }
        if (dto.getFungiYeastCountId() != null) {
            conditions.add(getValidCondition(dto.getFungiYeastCountId(), EnumTest.FUNGI_YEAST_COUNT));
        }
        if (dto.getPathogensId() != null) {
            conditions.add(getValidCondition(dto.getPathogensId(), EnumTest.PATHOGENS));
        }

        return conditions;
    }

    private Condition getValidCondition(Long conditionId, EnumTest expectedType) {
        Condition condition = conditionRepository.findById(conditionId)
                .orElseThrow(() -> new EntityNotFoundException("La condición con ID " + conditionId + " no existe"));

        if (!condition.getType().equals(expectedType)) {
            throw new IllegalArgumentException("La condición con ID " + conditionId + " no corresponde al tipo esperado: " + expectedType);
        }

        return condition;
    }
}


