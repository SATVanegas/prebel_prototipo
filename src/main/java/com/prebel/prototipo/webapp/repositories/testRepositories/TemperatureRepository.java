package com.prebel.prototipo.webapp.repositories.testRepositories;

import com.prebel.prototipo.webapp.models.tests.Temperature;
import org.springframework.data.repository.CrudRepository;

public interface TemperatureRepository extends CrudRepository<Temperature, Long> {
}
