package com.prebel.prototipo.webapp.repositories.laboratory_reports_repositories;

import com.prebel.prototipo.webapp.models.laboratory_reports.Inspection;
import org.springframework.data.repository.CrudRepository;

public interface InspectionRepository extends CrudRepository<Inspection, Long> {
}
