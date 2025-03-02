package com.prebel.prototipo.webapp.repositories.laboratory_reports_repositories;

import com.prebel.prototipo.webapp.models.laboratory_reports.StabilitiesMatrix;
<<<<<<< Updated upstream
<<<<<<< Updated upstream
<<<<<<< Updated upstream
=======
=======
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
>>>>>>> Stashed changes
import org.springframework.data.repository.CrudRepository;

<<<<<<< Updated upstream
<<<<<<< Updated upstream
<<<<<<< Updated upstream
public interface StabilitiesMatrixRepository extends CrudRepository<StabilitiesMatrix, Long> {
=======
public interface StabilitiesMatrixRepository extends JpaRepository<StabilitiesMatrix, Long> {
    Optional<StabilitiesMatrix> findByProductId(Long productId);
>>>>>>> Stashed changes
=======
public interface StabilitiesMatrixRepository extends JpaRepository<StabilitiesMatrix, Long> {
    Optional<StabilitiesMatrix> findByProductId(Long productId);
>>>>>>> Stashed changes
=======
public interface StabilitiesMatrixRepository extends JpaRepository<StabilitiesMatrix, Long> {
    Optional<StabilitiesMatrix> findByProductId(Long productId);
>>>>>>> Stashed changes
}
