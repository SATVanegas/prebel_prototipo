package com.prebel.prototipo.webapp.repositories.laboratory_reports_repositories;

import com.prebel.prototipo.webapp.models.laboratory_reports.Inspection;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface InspectionRepository extends CrudRepository<Inspection, Long> {
    Optional<Inspection> findTopByOrderByIdDesc();
}
