package com.prebel.prototipo.webapp.services.utils;

import com.prebel.prototipo.webapp.models.weekly_planner.DayWeek;
import org.springframework.stereotype.Service;

@Service
public class DateService {
    public DayWeek getDayFromString(String dayName) {
        for (DayWeek d : DayWeek.values()) {
            if (d.toString().equalsIgnoreCase(dayName)) {
                return d;
            }
        }
        throw new IllegalArgumentException("Nombre de día inválido: " + dayName);
    }
}
