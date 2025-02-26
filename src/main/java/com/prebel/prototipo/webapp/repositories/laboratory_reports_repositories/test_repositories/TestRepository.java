package com.prebel.prototipo.webapp.repositories.laboratory_reports_repositories.test_repositories;

import com.prebel.prototipo.webapp.models.laboratory_reports.tests.Test;
import org.springframework.data.repository.CrudRepository;

public interface TestRepository extends CrudRepository<Test, Long> {
}
