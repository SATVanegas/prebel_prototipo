package com.prebel.prototipo.webapp.controllers.laboratory_reports_controllers;

import com.prebel.prototipo.webapp.dtos.validations.laboratory_reports_requests.GetProductDTO;
import com.prebel.prototipo.webapp.dtos.validations.laboratory_reports_requests.ProductDTO;
import com.prebel.prototipo.webapp.models.laboratory_reports.Product;
import com.prebel.prototipo.webapp.models.laboratory_reports.StabilitiesMatrix;
import com.prebel.prototipo.webapp.services.laboratory_reports_services.ProductService;
import com.prebel.prototipo.webapp.services.laboratory_reports_services.StabilitiesMatrixService;
import com.prebel.prototipo.webapp.services.utils.PdfReportService;
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
    private final StabilitiesMatrixService stabilitiesMatrixService;
    private final PdfReportService pdfReportService;
    public ProductController(ProductService productService, StabilitiesMatrixService stabilitiesMatrixService, PdfReportService pdfReportService) { this.productService = productService;
        this.stabilitiesMatrixService = stabilitiesMatrixService;
        this.pdfReportService = pdfReportService;
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Optional<Product> product = productService.getProductById(id);
        return product.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear un nuevo Condition
    @PostMapping
    public ResponseEntity<String> createProduct(@Valid @RequestBody ProductDTO dto) {
        productService.createProduct(dto);
        return ResponseEntity.ok("Product creado correctamente");
    }

    @GetMapping
    public ResponseEntity<List<GetProductDTO>> getAllProducts() {
        List<GetProductDTO> products = productService.getAllProducts().stream()
                .map(product -> {
                    Long stabilitiesMatrixId = stabilitiesMatrixService.getStabilitiesMatrixByProductId(product.getId())
                            .map(StabilitiesMatrix::getId)
                            .orElse(null);
                    return new GetProductDTO(product.getId(), product.getProductDescription(), product.getBrand(), stabilitiesMatrixId);
                })
                .toList();
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
    public ResponseEntity<byte[]> verPdfEnNavegador() {
        byte[] pdfBytes = pdfReportService.createProductReport(productService.crearProductoDePrueba());

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=product_report.pdf") // "inline" lo muestra sin descargar
                .body(pdfBytes);
    }


}
