package com.prebel.prototipo.webapp.controllers;

import com.prebel.prototipo.webapp.models.Product;
import com.prebel.prototipo.webapp.repositories.ProductRepository;
import com.prebel.prototipo.webapp.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public ProductController(ProductRepository productRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    // Buscar un Product por ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Optional<Product> product = productRepository.findById(id);
        return product.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear un nuevo Product
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        // Asignar relaciones con usuarios si existen
        product.setCustomer(userRepository.findById(product.getCustomer().getId()).orElse(null));
        product.setResponsibleChemist(userRepository.findById(product.getResponsibleChemist().getId()).orElse(null));
        product.setResponsibleEngineer(userRepository.findById(product.getResponsibleEngineer().getId()).orElse(null));
        product.setResponsibleAnalyst(userRepository.findById(product.getResponsibleAnalyst().getId()).orElse(null));
        product.setTechnicianInCharge(userRepository.findById(product.getTechnicianInCharge().getId()).orElse(null));

        Product savedProduct = productRepository.save(product);
        return ResponseEntity.ok(savedProduct);
    }
}
