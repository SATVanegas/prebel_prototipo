package com.prebel.prototipo.webapp.services.weekly_planner_services;

import com.prebel.prototipo.webapp.dtos.validations.weekly_planner_request.TechnicianScheduleDTO;
import com.prebel.prototipo.webapp.models.weekly_planner.TechnicianSchedule;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TechnicianScheduleService {
    public void createTechnicianSchedule(@Valid TechnicianScheduleDTO dto) {
    }

    public Optional<TechnicianSchedule> getTechnicianSchedule(Long id) {
        return Optional.empty();
    }
}