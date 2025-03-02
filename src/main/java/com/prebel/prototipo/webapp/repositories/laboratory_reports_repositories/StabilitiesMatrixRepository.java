package com.prebel.prototipo.webapp.repositories.laboratory_reports_repositories;

import com.prebel.prototipo.webapp.models.laboratory_reports.StabilitiesMatrix;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StabilitiesMatrixRepository extends CrudRepository<StabilitiesMatrix, Long> {


    @Query("SELECT sm FROM StabilitiesMatrix sm JOIN sm.inspections i WHERE i.expectedDate BETWEEN :startDate AND :endDate")
    List<StabilitiesMatrix> findInspectionsWithinDateRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate);


    Optional<StabilitiesMatrix> findByProductId(Long productId);

}
import java.util.Optional;

public interface StabilitiesMatrixRepository extends JpaRepository<StabilitiesMatrix, Long> {
    Optional<StabilitiesMatrix> findByProductId(Long productId);
}