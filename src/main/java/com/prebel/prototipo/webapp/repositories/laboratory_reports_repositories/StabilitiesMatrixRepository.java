package com.prebel.prototipo.webapp.repositories.laboratory_reports_repositories;

import com.prebel.prototipo.webapp.models.laboratory_reports.Inspection;
import com.prebel.prototipo.webapp.models.laboratory_reports.StabilitiesMatrix;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StabilitiesMatrixRepository extends JpaRepository<StabilitiesMatrix, Long> {


    @Query("SELECT i FROM Inspection i WHERE i.expectedDate BETWEEN :startDate AND :endDate AND i.realDate IS NULL")
    List<Inspection> findInspectionsByExpectedDateBetween(@Param("startDate") Date startDate, @Param("endDate") Date endDate);


    Optional<StabilitiesMatrix> findByProductId(Long productId);

}
