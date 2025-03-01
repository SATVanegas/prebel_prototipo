package com.prebel.prototipo.webapp.services.laboratory_reports_services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import com.prebel.prototipo.webapp.dtos.validations.laboratory_reports_requests.ProductDTO;
import com.prebel.prototipo.webapp.models.laboratory_reports.Product;
import com.prebel.prototipo.webapp.models.role_module.User;
import com.prebel.prototipo.webapp.repositories.laboratory_reports_repositories.ProductRepository;
import com.prebel.prototipo.webapp.services.role_module_services.UserService;
import com.prebel.prototipo.webapp.services.utils.PdfReportService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserService userService;

    @Mock
    private PdfReportService pdfReportService;

    @InjectMocks
    private ProductService productService;

    private ProductDTO productDTO;
    private User customer;
    private User responsibleChemist;
    private User responsibleEngineer;
    private User responsibleAnalyst;
    private User technicianInCharge;
    private Product product;

    @BeforeEach
    void setUp() {
        productDTO = new ProductDTO();

        product = new Product(productDTO);
    }

    @Test
    void getProductById_Success() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Optional<Product> result = productService.getProductById(1L);

        assertTrue(result.isPresent());

        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void getProductById_NotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Product> result = productService.getProductById(1L);

        assertFalse(result.isPresent());

        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void createProduct_Success() {
        when(productRepository.save(any(Product.class))).thenReturn(product);

        productService.createProduct(productDTO);

        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void createProduct_UserNotFound() {
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void generateReport_Success() {
        // Simular un producto existente
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(pdfReportService.createProductReport(product)).thenReturn(new byte[]{1, 2, 3}); // Simulamos un PDF en bytes

        // Ejecutar el método
        byte[] pdfBytes = productService.generateReport(1L);

        // Verificar que el PDF no sea nulo ni vacío
        assertNotNull(pdfBytes);
        assertTrue(pdfBytes.length > 0);

        // Verificar interacciones
        verify(productRepository, times(1)).findById(1L);
        verify(pdfReportService, times(1)).createProductReport(product);
    }

    @Test
    void generateReport_ProductNotFound() {
        // Simular que el producto no existe
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        // Ejecutar el método y verificar que lanza la excepción correcta
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            productService.generateReport(1L);
        });

        // Verificar el mensaje y el código de error
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Producto no encontrado", exception.getReason());

        // Verificar que no se llama al servicio de PDF
        verify(productRepository, times(1)).findById(1L);
        verify(pdfReportService, never()).createProductReport(any(Product.class));
    }

}