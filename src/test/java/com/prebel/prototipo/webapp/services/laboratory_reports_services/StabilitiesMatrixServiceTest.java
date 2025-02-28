package com.prebel.prototipo.webapp.services.laboratory_reports_services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import com.prebel.prototipo.webapp.dtos.validations.laboratory_reports_requests.StabilitiesMatrixDTO;
import com.prebel.prototipo.webapp.models.laboratory_reports.Product;
import com.prebel.prototipo.webapp.models.laboratory_reports.StabilitiesMatrix;
import com.prebel.prototipo.webapp.models.role_module.User;
import com.prebel.prototipo.webapp.repositories.laboratory_reports_repositories.StabilitiesMatrixRepository;
import com.prebel.prototipo.webapp.services.laboratory_reports_services.ProductService;
import com.prebel.prototipo.webapp.services.laboratory_reports_services.StabilitiesMatrixService;
import com.prebel.prototipo.webapp.services.role_module_services.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class StabilitiesMatrixServiceTest {

    @Mock
    private StabilitiesMatrixRepository stabilitiesMatrixRepository;

    @Mock
    private ProductService productService;

    @Mock
    private UserService userService;

    @InjectMocks
    private StabilitiesMatrixService stabilitiesMatrixService;

    private StabilitiesMatrixDTO stabilitiesMatrixDTO;
    private Product product;
    private User customer;
    private User chemical;
    private User engineer;
    private StabilitiesMatrix stabilitiesMatrix;

    @BeforeEach
    void setUp() {
        stabilitiesMatrixDTO = new StabilitiesMatrixDTO();
        stabilitiesMatrixDTO.setProductId(1L);
        stabilitiesMatrixDTO.setCustomerId(2L);
        stabilitiesMatrixDTO.setChemicalId(3L);
        stabilitiesMatrixDTO.setEngineerId(4L);

        product = new Product();
        product.setId(1L);

        customer = new User();
        customer.setId(2L);

        chemical = new User();
        chemical.setId(3L);

        engineer = new User();
        engineer.setId(4L);

        stabilitiesMatrix = new StabilitiesMatrix(stabilitiesMatrixDTO, product, customer, chemical, engineer);
    }

    @Test
    void getStabilitiesMatrixById_Success() {
        when(stabilitiesMatrixRepository.findById(1L)).thenReturn(Optional.of(stabilitiesMatrix));

        Optional<StabilitiesMatrix> result = stabilitiesMatrixService.getStabilitiesMatrixById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getProduct().getId());
        assertEquals(2L, result.get().getCustomer().getId());
        assertEquals(3L, result.get().getChemical().getId());
        assertEquals(4L, result.get().getEngineer().getId());

        verify(stabilitiesMatrixRepository, times(1)).findById(1L);
    }

    @Test
    void getStabilitiesMatrixById_NotFound() {
        when(stabilitiesMatrixRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<StabilitiesMatrix> result = stabilitiesMatrixService.getStabilitiesMatrixById(1L);

        assertFalse(result.isPresent());

        verify(stabilitiesMatrixRepository, times(1)).findById(1L);
    }

    @Test
    void createStabilitiesMatrix_Success() {
        when(productService.getProductById(1L)).thenReturn(Optional.of(product));
        when(userService.getUserById(2L)).thenReturn(Optional.of(customer));
        when(userService.getUserById(3L)).thenReturn(Optional.of(chemical));
        when(userService.getUserById(4L)).thenReturn(Optional.of(engineer));
        when(stabilitiesMatrixRepository.save(any(StabilitiesMatrix.class))).thenReturn(stabilitiesMatrix);

        stabilitiesMatrixService.createStabilitiesMatrix(stabilitiesMatrixDTO);

        verify(productService, times(1)).getProductById(1L);
        verify(userService, times(1)).getUserById(2L);
        verify(userService, times(1)).getUserById(3L);
        verify(userService, times(1)).getUserById(4L);
        verify(stabilitiesMatrixRepository, times(1)).save(any(StabilitiesMatrix.class));
    }

    @Test
    void createStabilitiesMatrix_ProductNotFound() {
        when(productService.getProductById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            stabilitiesMatrixService.createStabilitiesMatrix(stabilitiesMatrixDTO);
        });

        assertEquals("El producto con ID 1 no existe", exception.getMessage());

        verify(productService, times(1)).getProductById(1L);
        verify(userService, never()).getUserById(anyLong());
        verify(stabilitiesMatrixRepository, never()).save(any(StabilitiesMatrix.class));
    }

    @Test
    void createStabilitiesMatrix_UserrNotFound() {
        when(productService.getProductById(1L)).thenReturn(Optional.of(product));
        when(userService.getUserById(2L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            stabilitiesMatrixService.createStabilitiesMatrix(stabilitiesMatrixDTO);
        });

        assertEquals("El cliente con ID 2 no existe", exception.getMessage());

        verify(productService, times(1)).getProductById(1L);
        verify(userService, times(1)).getUserById(2L);
        verify(userService, never()).getUserById(3L);
        verify(userService, never()).getUserById(4L);
        verify(stabilitiesMatrixRepository, never()).save(any(StabilitiesMatrix.class));
    }

}