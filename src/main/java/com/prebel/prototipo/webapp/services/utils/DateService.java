package com.prebel.prototipo.webapp.services.utils;

import com.prebel.prototipo.webapp.models.weekly_planner.DayWeek;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
public class DateService {

    public Optional<DayWeek> getDayFromString(String nombreDia) {
        return Arrays.stream(DayWeek.values())
                .filter(diaSemana -> diaSemana.toString().equalsIgnoreCase(nombreDia))
                .findFirst();
    }
}
