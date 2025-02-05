package com.prebel.prototipo.webapp.repositories.weekly_planner_repositories;

import com.prebel.prototipo.webapp.models.weekly_planner.WeeklyCalendar;
import org.springframework.data.repository.CrudRepository;

public interface WeeklyCalendarRepository extends CrudRepository<WeeklyCalendar, Long> {
}
