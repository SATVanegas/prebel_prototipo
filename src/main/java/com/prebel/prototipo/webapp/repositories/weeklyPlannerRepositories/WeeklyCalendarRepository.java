package com.prebel.prototipo.webapp.repositories.weeklyPlannerRepositories;

import com.prebel.prototipo.webapp.models.weekly_planner.WeeklyCalendar;
import org.springframework.data.repository.CrudRepository;

public interface WeeklyCalendarRepository extends CrudRepository<WeeklyCalendar, Long> {
}
