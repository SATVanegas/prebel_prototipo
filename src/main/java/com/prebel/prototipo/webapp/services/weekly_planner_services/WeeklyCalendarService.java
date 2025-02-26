package com.prebel.prototipo.webapp.services.weekly_planner_services;

import com.prebel.prototipo.webapp.dtos.validations.weekly_planner_request.WeeklyCalendarDTO;
import com.prebel.prototipo.webapp.models.weekly_planner.WeeklyCalendar;
import com.prebel.prototipo.webapp.repositories.weekly_planner_repositories.WeeklyCalendarRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class WeeklyCalendarService {

    private final WeeklyCalendarRepository weeklyCalendarRepository;

    public WeeklyCalendarService(WeeklyCalendarRepository weeklyCalendarRepository) {
        this.weeklyCalendarRepository = weeklyCalendarRepository;
    }

    public Optional<WeeklyCalendar> getWeeklyCalendarById(Long id) {
        return weeklyCalendarRepository.findById(id);
    }

    public void createWeeklyCalendar(@Valid WeeklyCalendarDTO dto) {
        WeeklyCalendar weeklyCalendar = new WeeklyCalendar(dto);
        weeklyCalendarRepository.save(weeklyCalendar);
    }
}
