package com.prebel.prototipo.webapp.controllers.laboratory_reports_controllers;

import com.prebel.prototipo.webapp.models.laboratory_reports.Product;
import com.prebel.prototipo.webapp.repositories.laboratory_reports_repositories.ProductRepository;
import com.prebel.prototipo.webapp.repositories.role_module_repositories.UserRepository;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<?> createProduct(@RequestBody Product product) {
        // Validar y asignar relaciones con usuarios si existen
        if (product.getCustomer() == null || userRepository.findById(product.getCustomer().getId()).isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cliente no encontrado.");
        }
        product.setCustomer(userRepository.findById(product.getCustomer().getId()).get());

        if (product.getResponsibleChemist() == null || userRepository.findById(product.getResponsibleChemist().getId()).isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Químico responsable no encontrado.");
        }
        product.setResponsibleChemist(userRepository.findById(product.getResponsibleChemist().getId()).get());

        if (product.getResponsibleEngineer() == null || userRepository.findById(product.getResponsibleEngineer().getId()).isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ingeniero responsable no encontrado.");
        }
        product.setResponsibleEngineer(userRepository.findById(product.getResponsibleEngineer().getId()).get());

        if (product.getResponsibleAnalyst() == null || userRepository.findById(product.getResponsibleAnalyst().getId()).isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Analista responsable no encontrado.");
        }
        product.setResponsibleAnalyst(userRepository.findById(product.getResponsibleAnalyst().getId()).get());

        if (product.getTechnicianInCharge() == null || userRepository.findById(product.getTechnicianInCharge().getId()).isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Técnico a cargo no encontrado.");
        }
        product.setTechnicianInCharge(userRepository.findById(product.getTechnicianInCharge().getId()).get());

        Product savedProduct = productRepository.save(product);
        return ResponseEntity.ok(savedProduct);
    }
}
