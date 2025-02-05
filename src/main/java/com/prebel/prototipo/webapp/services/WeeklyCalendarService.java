package com.prebel.prototipo.webapp.services;

import com.prebel.prototipo.webapp.models.weekly_planner.TechnicianSchedule;
import com.prebel.prototipo.webapp.models.weekly_planner.WeeklyCalendar;
import com.prebel.prototipo.webapp.repositories.weekly_planner_repositories.TechnicianScheduleRepository;
import com.prebel.prototipo.webapp.repositories.weekly_planner_repositories.WeeklyCalendarRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class WeeklyCalendarService {

    private final TechnicianScheduleRepository technicianScheduleRepository;
    private final WeeklyCalendarRepository weeklyCalendarRepository;

    public WeeklyCalendarService(TechnicianScheduleRepository technicianScheduleRepository, WeeklyCalendarRepository weeklyCalendarRepository) {
        this.technicianScheduleRepository = technicianScheduleRepository;
        this.weeklyCalendarRepository = weeklyCalendarRepository;
    }

    // Método para validar los TechnicianSchedules por sus IDs
    public List<TechnicianSchedule> validateTechnicianSchedules(List<Integer> technicianScheduleIds) {
        List<Long> ids = technicianScheduleIds.stream()
                .map(Long::valueOf)
                .collect(Collectors.toList());

        Iterable<TechnicianSchedule> technicianScheduleIterable = technicianScheduleRepository.findAllById(ids);

        // Convertir el Iterable a List
        List<TechnicianSchedule> technicianSchedules = StreamSupport.stream(technicianScheduleIterable.spliterator(), false)
                .collect(Collectors.toList());

        // Verificar si todos los TechnicianSchedules fueron encontrados
        if (technicianSchedules.size() != technicianScheduleIds.size()) {
            throw new IllegalArgumentException("Algunos IDs de TechnicianSchedule no fueron encontrados.");
        }

        return technicianSchedules;
    }

    // Método para crear el WeeklyCalendar
    public WeeklyCalendar createWeeklyCalendar(List<TechnicianSchedule> technicianSchedules) {
        WeeklyCalendar weeklyCalendar = new WeeklyCalendar();
        weeklyCalendar.setTechniciansSchedules(technicianSchedules);

        return weeklyCalendarRepository.save(weeklyCalendar);
    }
}
