package com.prebel.prototipo.webapp.repositories.laboratory_reports_repositories;

import com.prebel.prototipo.webapp.models.laboratory_reports.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
}
