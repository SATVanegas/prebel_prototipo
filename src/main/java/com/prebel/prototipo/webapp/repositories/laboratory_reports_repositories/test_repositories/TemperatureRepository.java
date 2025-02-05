package com.prebel.prototipo.webapp.repositories.laboratory_reports_repositories.test_repositories;

import com.prebel.prototipo.webapp.models.laboratory_reports.tests.Temperature;
import org.springframework.data.repository.CrudRepository;

public interface TemperatureRepository extends CrudRepository<Temperature, Long> {
}
