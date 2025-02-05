package com.prebel.prototipo.webapp.repositories.weekly_planner_repositories;

import com.prebel.prototipo.webapp.models.role_module.User;
import com.prebel.prototipo.webapp.models.role_module.Role;
import com.prebel.prototipo.webapp.models.weekly_planner.WeeklyCalendar;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WeeklyCalendarRepository extends CrudRepository<WeeklyCalendar, Long> {
    List<WeeklyCalendar> findByAssignedUser(User user);

    List<WeeklyCalendar> findByAssignedRole(Role role);

    void deleteByAssignedUser(User user);

    void deleteByAssignedRole(Role role);
}
