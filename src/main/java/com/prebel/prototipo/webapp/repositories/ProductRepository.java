package com.prebel.prototipo.webapp.repositories;

import com.prebel.prototipo.webapp.models.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
}
