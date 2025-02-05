package com.prebel.prototipo.webapp.repositories.weekly_planner_repositories;

import com.prebel.prototipo.webapp.models.weekly_planner.TechnicianSchedule;
import com.prebel.prototipo.webapp.models.weekly_planner.WeeklyCalendar;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public interface WeeklyCalendarRepository extends CrudRepository<WeeklyCalendar, Long> {

    // Obtener las tareas del técnico para un día específico
    default List<TechnicianSchedule> findTechnicianSchedulesByDay(WeeklyCalendar weeklyCalendar, String day) {
        return weeklyCalendar.getTechniciansSchedules().stream()
                .filter(schedule -> schedule.getDayOfWeek().equalsIgnoreCase(day))
                .collect(Collectors.toList());
    }

    // Eliminar tareas de un día específico
    default boolean deleteDayTasks(WeeklyCalendar weeklyCalendar, String day) {
        List<TechnicianSchedule> schedulesToRemove = weeklyCalendar.getTechniciansSchedules().stream()
                .filter(schedule -> schedule.getDayOfWeek().equalsIgnoreCase(day))
                .toList();

        weeklyCalendar.getTechniciansSchedules().removeAll(schedulesToRemove);
        save(weeklyCalendar);
        return !schedulesToRemove.isEmpty();
    }

    // Actualizar tareas de un día específico
    default WeeklyCalendar updateDayTasks(WeeklyCalendar weeklyCalendar, String day, List<TechnicianSchedule> updatedSchedules) {
        weeklyCalendar.getTechniciansSchedules().removeIf(schedule -> schedule.getDayOfWeek().equalsIgnoreCase(day));
        updatedSchedules.forEach(schedule -> schedule.setDayOfWeek(day)); // Actualizar día de la semana
        weeklyCalendar.getTechniciansSchedules().addAll(updatedSchedules);
        return save(weeklyCalendar);
    }
}

