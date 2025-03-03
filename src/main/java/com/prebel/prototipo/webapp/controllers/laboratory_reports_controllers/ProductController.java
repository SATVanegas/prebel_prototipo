package com.prebel.prototipo.webapp.controllers.laboratory_reports_controllers;

import com.prebel.prototipo.webapp.dtos.validations.laboratory_reports_requests.GetProductDTO;
import com.prebel.prototipo.webapp.dtos.validations.laboratory_reports_requests.ProductDTO;
import com.prebel.prototipo.webapp.models.laboratory_reports.Product;
import com.prebel.prototipo.webapp.models.laboratory_reports.StabilitiesMatrix;
import com.prebel.prototipo.webapp.services.laboratory_reports_services.ProductService;
import com.prebel.prototipo.webapp.services.laboratory_reports_services.StabilitiesMatrixService;
import com.prebel.prototipo.webapp.services.utils.PdfReportService;
import com.prebel.prototipo.webapp.services.utils.EmailServicePdf;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final StabilitiesMatrixService stabilitiesMatrixService;
    private final PdfReportService pdfReportService;
    private final EmailServicePdf emailServicePdf;

    public ProductController(ProductService productService,
                             StabilitiesMatrixService stabilitiesMatrixService,
                             PdfReportService pdfReportService,
                             EmailServicePdf emailServicePdf) {
        this.productService = productService;
        this.stabilitiesMatrixService = stabilitiesMatrixService;
        this.pdfReportService = pdfReportService;
        this.emailServicePdf = emailServicePdf;
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

    @GetMapping("/{id}/send-report")
    public ResponseEntity<String> enviarReportePorCorreo(@PathVariable Long id, @RequestParam String email) {
        try {
            byte[] pdf = productService.generateReport(id);
            emailServicePdf.enviarCorreoConAdjunto(email, "Reporte de Producto - Prebel", pdf);

            return ResponseEntity.ok("Correo enviado exitosamente a " + email);
        } catch (MessagingException | IOException e) {
            return ResponseEntity.status(500).body("Error al enviar el correo: " + e.getMessage());
        }
    }
}
