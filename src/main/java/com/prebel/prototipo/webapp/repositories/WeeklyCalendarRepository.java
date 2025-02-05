package com.prebel.prototipo.webapp.repositories;

import com.prebel.prototipo.webapp.models.User;
import com.prebel.prototipo.webapp.models.WeeklyCalendar;
import com.prebel.prototipo.webapp.models.role_module.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WeeklyCalendarRepository extends CrudRepository<WeeklyCalendar, Long> {
    List<WeeklyCalendar> findByAssignedUser(User user);

    List<WeeklyCalendar> findByAssignedRole(Role role);

    void deleteByAssignedUser(User user);

    void deleteByAssignedRole(Role role);
}
