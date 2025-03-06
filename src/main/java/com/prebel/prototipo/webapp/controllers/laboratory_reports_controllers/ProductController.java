package com.prebel.prototipo.webapp.controllers.laboratory_reports_controllers;

import com.prebel.prototipo.webapp.dtos.validations.laboratory_reports_requests.GetProductDTO;
import com.prebel.prototipo.webapp.dtos.validations.laboratory_reports_requests.ProductDTO;
import com.prebel.prototipo.webapp.models.laboratory_reports.Product;
import com.prebel.prototipo.webapp.services.laboratory_reports_services.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Buscar por ID
    @GetMapping("/dto/{id}")
    public ResponseEntity<GetProductDTO> getProductDTOById(@PathVariable Long id) {
        GetProductDTO product = productService.getProductDTOById(id);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Optional<Product> product = productService.getProductById(id);
        return product.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear un nuevo Product
    @PostMapping
    public ResponseEntity<String> createProduct(@Valid @RequestBody ProductDTO dto) {
        productService.createProduct(dto);
        return ResponseEntity.ok("Product creado correctamente");
    }

    @GetMapping
    public ResponseEntity<List<GetProductDTO>> getAllProducts() {
        List<GetProductDTO> products = productService.getAllProductsWithStabilityMatrix();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}/generate-report")
    public ResponseEntity<byte[]> generateProductReport(@PathVariable Long id) {
        byte[] pdf = productService.generateReport(id);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=product_report.pdf")
                .body(pdf);
    }

    @GetMapping("/product/report/view")
    public ResponseEntity<byte[]> viewPdfInBrowser() {
        byte[] pdfBytes = productService.generateTestProductReport();
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=product_report.pdf")
                .body(pdfBytes);
    }

    @GetMapping("/{id}/send-report")
    public ResponseEntity<String> sendReportByEmail(@PathVariable Long id, @RequestParam String email) {
        return productService.sendReportByEmail(id, email);
    }
}
