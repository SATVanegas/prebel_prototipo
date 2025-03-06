package com.prebel.prototipo.webapp.services.laboratory_reports_services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.prebel.prototipo.webapp.dtos.validations.laboratory_reports_requests.GetProductDTO;
import com.prebel.prototipo.webapp.dtos.validations.laboratory_reports_requests.ProductDTO;
import com.prebel.prototipo.webapp.models.laboratory_reports.Product;
import com.prebel.prototipo.webapp.models.laboratory_reports.StabilitiesMatrix;
import com.prebel.prototipo.webapp.repositories.laboratory_reports_repositories.ProductRepository;
import com.prebel.prototipo.webapp.services.utils.DefaultProductService;
import com.prebel.prototipo.webapp.services.utils.EmailServicePdf;
import com.prebel.prototipo.webapp.services.utils.PdfReportService;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final PdfReportService pdfReportService;
    private final DefaultProductService defaultProductService;
    private final StabilitiesMatrixService stabilitiesMatrixService;
    private final EmailServicePdf emailServicePdf;

    public ProductService(
            ProductRepository productRepository,
            PdfReportService pdfReportService,
            DefaultProductService defaultProductService,
            StabilitiesMatrixService stabilitiesMatrixService,
            EmailServicePdf emailServicePdf) {
        this.productRepository = productRepository;
        this.pdfReportService = pdfReportService;
        this.defaultProductService = defaultProductService;
        this.stabilitiesMatrixService = stabilitiesMatrixService;
        this.emailServicePdf = emailServicePdf;
    }

    // ===================== Métodos de Consulta =====================

    public List<Product> getAllProducts() {
        return (List<Product>) productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public GetProductDTO getProductDTOById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));
        return mapToDTO(product);
    }

    public List<GetProductDTO> getAllProductsWithStabilityMatrix() {
        return getAllProducts().stream()
                .map(product -> {
                    Long stabilityMatrixId = stabilitiesMatrixService.getStabilitiesMatrixByProductId(product.getId())
                            .map(StabilitiesMatrix::getId)
                            .orElse(null);
                    return new GetProductDTO(product.getId(), product.getProductDescription(), product.getBrand(), stabilityMatrixId);
                })
                .toList();
    }

    private GetProductDTO mapToDTO(Product product) {
        Long stabilityMatrixId = product.getStabilitiesMatrix() != null
                ? product.getStabilitiesMatrix().getId()
                : null;

        return new GetProductDTO(product.getId(), product.getProductDescription(), product.getBrand(), stabilityMatrixId);
    }

    // ===================== Métodos de Creación =====================

    public void createProduct(@Valid ProductDTO dto) {
        Product product = new Product(dto);
        productRepository.save(product);
    }

    // ===================== Métodos de Reportes =====================

    public byte[] generateReport(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));

        return pdfReportService.createProductReport(product);
    }

    public byte[] generateTestProductReport() {
        return pdfReportService.createProductReport(defaultProductService.crearProductoDePrueba());
    }

    // ===================== Métodos de Envío de Correo =====================

    public ResponseEntity<String> sendReportByEmail(Long productId, String email) {
        try {
            byte[] pdf = generateReport(productId);
            emailServicePdf.enviarCorreoConAdjunto(email, "Reporte de Producto - Prebel", pdf);
            return ResponseEntity.ok("Correo enviado exitosamente a " + email);
        } catch (MessagingException | IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al enviar el correo: " + e.getMessage());
        }
    }
}

