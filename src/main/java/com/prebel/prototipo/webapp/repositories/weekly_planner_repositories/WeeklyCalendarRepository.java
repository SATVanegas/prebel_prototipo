package com.prebel.prototipo.webapp.repositories.weekly_planner_repositories;

import com.prebel.prototipo.webapp.models.weekly_planner.TechnicianSchedule;
import com.prebel.prototipo.webapp.models.weekly_planner.WeeklyCalendar;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.List;

public interface WeeklyCalendarRepository extends CrudRepository<WeeklyCalendar, Long> {
    default List<TechnicianSchedule> findTechnicianSchedulesByDay(WeeklyCalendar weeklyCalendar, String day) {
        return switch (day.toLowerCase()) {
            case "monday" -> weeklyCalendar.getMondayTechniciansSchedule();
            case "tuesday" -> weeklyCalendar.getTuesdayTechniciansSchedule();
            case "wednesday" -> weeklyCalendar.getWednesdayTechniciansSchedule();
            case "thursday" -> weeklyCalendar.getThursdayTechniciansSchedule();
            case "friday" -> weeklyCalendar.getFridayTechniciansSchedule();
            case "saturday" -> weeklyCalendar.getSaturdayTechniciansSchedule();
            default -> null;
        };
    }
    default boolean deleteDayTasks(WeeklyCalendar weeklyCalendar, String day) {
        switch (day.toLowerCase()) {
            case "monday" -> weeklyCalendar.setMondayTechniciansSchedule(new ArrayList<>());
            case "tuesday" -> weeklyCalendar.setTuesdayTechniciansSchedule(new ArrayList<>());
            case "wednesday" -> weeklyCalendar.setWednesdayTechniciansSchedule(new ArrayList<>());
            case "thursday" -> weeklyCalendar.setThursdayTechniciansSchedule(new ArrayList<>());
            case "friday" -> weeklyCalendar.setFridayTechniciansSchedule(new ArrayList<>());
            case "saturday" -> weeklyCalendar.setSaturdayTechniciansSchedule(new ArrayList<>());
            default -> {
                return false; // Día no válido
            }
        }
        save(weeklyCalendar);
        return true;
    }
    default WeeklyCalendar updateDayTasks(WeeklyCalendar weeklyCalendar, String day, List<TechnicianSchedule> updatedSchedules) {
        switch (day.toLowerCase()) {
            case "monday" -> weeklyCalendar.setMondayTechniciansSchedule(updatedSchedules);
            case "tuesday" -> weeklyCalendar.setTuesdayTechniciansSchedule(updatedSchedules);
            case "wednesday" -> weeklyCalendar.setWednesdayTechniciansSchedule(updatedSchedules);
            case "thursday" -> weeklyCalendar.setThursdayTechniciansSchedule(updatedSchedules);
            case "friday" -> weeklyCalendar.setFridayTechniciansSchedule(updatedSchedules);
            case "saturday" -> weeklyCalendar.setSaturdayTechniciansSchedule(updatedSchedules);
            default -> {
                return null; // Día no válido
            }
        }

        return save(weeklyCalendar);
    }
}

