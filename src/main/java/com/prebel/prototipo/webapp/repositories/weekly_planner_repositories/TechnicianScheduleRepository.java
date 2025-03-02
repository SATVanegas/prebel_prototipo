package com.prebel.prototipo.webapp.repositories.weekly_planner_repositories;

import com.prebel.prototipo.webapp.models.role_module.User;
import com.prebel.prototipo.webapp.models.weekly_planner.TechnicianSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface TechnicianScheduleRepository extends JpaRepository<TechnicianSchedule, Long> {
    List<TechnicianSchedule> findByTechnician(User technician);

    @Query("SELECT ts FROM TechnicianSchedule ts JOIN FETCH ts.technician t WHERE t.id = :technicianId")
    List<TechnicianSchedule> findByTechnicianIdWithTechnician(@Param("technicianId") Long technicianId);

    long countByTechnicianId(Long technicianId);
}