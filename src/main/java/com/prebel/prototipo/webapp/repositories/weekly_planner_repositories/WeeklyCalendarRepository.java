package com.prebel.prototipo.webapp.repositories.weekly_planner_repositories;

import com.prebel.prototipo.webapp.models.weekly_planner.TechnicianSchedule;
import com.prebel.prototipo.webapp.models.weekly_planner.WeeklyCalendar;
import org.springframework.data.repository.CrudRepository;

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
}
