package com.prebel.prototipo.webapp.repositories.weekly_planner_repositories;

import com.prebel.prototipo.webapp.models.weekly_planner.TechnicianSchedule;
import org.springframework.data.repository.CrudRepository;

public interface TechnicianScheduleRepository extends CrudRepository<TechnicianSchedule, Long> {
}
