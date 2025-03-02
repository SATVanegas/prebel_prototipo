package com.prebel.prototipo.webapp.repositories.laboratory_reports_repositories;

import com.prebel.prototipo.webapp.models.laboratory_reports.StabilitiesMatrix;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface StabilitiesMatrixRepository extends CrudRepository<StabilitiesMatrix, Long> {
    Optional<StabilitiesMatrix> findByProductId(Long productId);
}
