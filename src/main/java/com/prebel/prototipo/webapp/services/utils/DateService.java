package com.prebel.prototipo.webapp.services.utils;

import com.prebel.prototipo.webapp.models.weekly_planner.DayWeek;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DateService {

    public Optional<DayWeek> getDayFromString(String day) {
        try {
            return Optional.of(DayWeek.valueOf(day));
        } catch (IllegalArgumentException e) {
            for (DayWeek dayWeek : DayWeek.values()) {
                if (dayWeek.getNombre().equalsIgnoreCase(day)) {
                    return Optional.of(dayWeek);
                }
            }
            return Optional.empty();
        }
    }
}
