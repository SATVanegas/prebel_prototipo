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
        productDTO.setCustomerId(1L);
        productDTO.setResponsibleChemistId(2L);
        productDTO.setResponsibleEngineerId(3L);
        productDTO.setResponsibleAnalystId(4L);
        productDTO.setTechnicianInChargeId(5L);

        customer = new User();
        customer.setId(1L);

        responsibleChemist = new User();
        responsibleChemist.setId(2L);

        responsibleEngineer = new User();
        responsibleEngineer.setId(3L);

        responsibleAnalyst = new User();
        responsibleAnalyst.setId(4L);

        technicianInCharge = new User();
        technicianInCharge.setId(5L);

        product = new Product(productDTO, customer, responsibleChemist, responsibleEngineer, responsibleAnalyst, technicianInCharge);
    }


    @Test
    void createProduct_Success() {
        when(userService.getUserById(1L)).thenReturn(Optional.of(customer));
        when(userService.getUserById(2L)).thenReturn(Optional.of(responsibleChemist));
        when(userService.getUserById(3L)).thenReturn(Optional.of(responsibleEngineer));
        when(userService.getUserById(4L)).thenReturn(Optional.of(responsibleAnalyst));
        when(userService.getUserById(5L)).thenReturn(Optional.of(technicianInCharge));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        productService.createProduct(productDTO);

        verify(userService, times(1)).getUserById(1L);
        verify(userService, times(1)).getUserById(2L);
        verify(userService, times(1)).getUserById(3L);
        verify(userService, times(1)).getUserById(4L);
        verify(userService, times(1)).getUserById(5L);
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void createProduct_UserNotFound() {
        when(userService.getUserById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            productService.createProduct(productDTO);
        });

        assertEquals("El cliente con ID 1 no existe", exception.getMessage());

        verify(userService, times(1)).getUserById(1L);
        verify(userService, never()).getUserById(2L);
        verify(userService, never()).getUserById(3L);
        verify(userService, never()).getUserById(4L);
        verify(userService, never()).getUserById(5L);
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