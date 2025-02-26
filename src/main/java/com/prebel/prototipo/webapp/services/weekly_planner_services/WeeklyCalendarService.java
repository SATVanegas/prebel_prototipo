package com.prebel.prototipo.webapp.services.weekly_planner_services;

import com.prebel.prototipo.webapp.dtos.validations.weekly_planner_request.WeeklyCalendarDTO;
import com.prebel.prototipo.webapp.models.weekly_planner.WeeklyCalendar;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class WeeklyCalendarService {
    public void createWeeklyCalendar(@Valid WeeklyCalendarDTO dto) {
    }

    public Optional<WeeklyCalendar> getWeeklyCalendar(Long id) {
        return Optional.empty();
    }
}
