package com.prebel.prototipo.webapp.repositories.laboratory_reports_repositories;

import com.prebel.prototipo.webapp.models.laboratory_reports.Inspection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface InspectionRepository extends CrudRepository<Inspection, Long> {
    Optional<Inspection> findTopByOrderByIdDesc();

    @Query("SELECT i FROM Inspection i WHERE i.expectedDate BETWEEN :startDate AND :endDate AND i.realDate IS NULL")
    List<Inspection> findInspectionsByExpectedDateBetween(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}

    @Query("SELECT i FROM Inspection i WHERE i.expectedDate BETWEEN :startDate AND :endDate")
    List<Inspection> findInspectionsWithinDateRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}