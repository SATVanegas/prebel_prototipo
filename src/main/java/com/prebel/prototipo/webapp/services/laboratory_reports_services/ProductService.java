package com.prebel.prototipo.webapp.services.laboratory_reports_services;

import com.prebel.prototipo.webapp.dtos.validations.laboratory_reports_requests.ProductDTO;
import com.prebel.prototipo.webapp.models.laboratory_reports.Product;
import com.prebel.prototipo.webapp.models.laboratory_reports.tests.Temperature;
import com.prebel.prototipo.webapp.models.role_module.User;
import com.prebel.prototipo.webapp.repositories.laboratory_reports_repositories.ProductRepository;
import com.prebel.prototipo.webapp.services.role_module_services.UserService;
import com.prebel.prototipo.webapp.services.utils.PdfReportService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final UserService userService;
    private final PdfReportService pdfReportService;

    public ProductService(ProductRepository productRepository, UserService userService, PdfReportService pdfReportService) {
        this.productRepository = productRepository;
        this.userService = userService;
        this.pdfReportService = pdfReportService;
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public void createProduct(@Valid ProductDTO dto) {
        User customer = userService.getUserById(dto.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("El cliente con ID " + dto.getCustomerId() + " no existe"));

        User responsibleChemist = userService.getUserById(dto.getResponsibleChemistId())
                .orElseThrow(() -> new EntityNotFoundException("El químico responsable con ID " + dto.getResponsibleChemistId() + " no existe"));

        User responsibleEngineer = userService.getUserById(dto.getResponsibleEngineerId())
                .orElseThrow(() -> new EntityNotFoundException("El ingeniero responsable con ID " + dto.getResponsibleEngineerId() + " no existe"));

        User responsibleAnalyst = userService.getUserById(dto.getResponsibleAnalystId())
                .orElseThrow(() -> new EntityNotFoundException("El analista responsable con ID " + dto.getResponsibleAnalystId() + " no existe"));

        User technicianInCharge = userService.getUserById(dto.getTechnicianInChargeId())
                .orElseThrow(() -> new EntityNotFoundException("El técnico encargado con ID " + dto.getTechnicianInChargeId() + " no existe"));

        Product product = new Product(dto, customer, responsibleChemist, responsibleEngineer, responsibleAnalyst, technicianInCharge);
        productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return (List<Product>) productRepository.findAll();
    }

    public byte[] generateReport(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));

        return pdfReportService.createProductReport(product);
    }

    public Product crearProductoDePrueba() {
        Product product = new Product();
        product.setProductDescription("Producto de prueba");
        product.setReference("REF123");
        product.setBatch("BATCH001");

        // Crear tests de prueba
        com.prebel.prototipo.webapp.models.laboratory_reports.tests.Test test1 =
                new com.prebel.prototipo.webapp.models.laboratory_reports.tests.Test();
        test1.setId(1L);
        test1.setObservations("Observación 1");
        test1.setConclusion("Conclusión 1");
        Temperature temp1 = new Temperature();
        temp1.setUnit("°C");
        test1.setTemperature(temp1);

        com.prebel.prototipo.webapp.models.laboratory_reports.tests.Test test2 = new com.prebel.prototipo.webapp.models.laboratory_reports.tests.Test();
        test2.setId(2L);
        test2.setObservations("Observación 2");
        test2.setConclusion("Conclusión 2");
        Temperature temp2 = new Temperature();
        temp2.setUnit("°F");
        test2.setTemperature(temp2);

        product.setTests(Arrays.asList(test1, test2));

        return product;
    }
}

