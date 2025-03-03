package com.prebel.prototipo.webapp.services.laboratory_reports_services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import com.prebel.prototipo.webapp.dtos.validations.laboratory_reports_requests.StabilitiesMatrixDTO;
import com.prebel.prototipo.webapp.models.laboratory_reports.Product;
import com.prebel.prototipo.webapp.models.laboratory_reports.StabilitiesMatrix;
import com.prebel.prototipo.webapp.repositories.laboratory_reports_repositories.StabilitiesMatrixRepository;
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

    @InjectMocks
    private StabilitiesMatrixService stabilitiesMatrixService;

    private StabilitiesMatrixDTO stabilitiesMatrixDTO;
    private Product product;
    private StabilitiesMatrix stabilitiesMatrix;

    @BeforeEach
    void setUp() {
        stabilitiesMatrixDTO = new StabilitiesMatrixDTO();
        stabilitiesMatrixDTO.setProductId(1L);

        product = new Product();
        product.setId(1L);

        stabilitiesMatrix = new StabilitiesMatrix(stabilitiesMatrixDTO, product);
    }

    @Test
    void createStabilitiesMatrix_Success() {
        when(stabilitiesMatrixRepository.save(any(StabilitiesMatrix.class))).thenReturn(stabilitiesMatrix);

        stabilitiesMatrixService.createStabilitiesMatrix(stabilitiesMatrixDTO);

        verify(productService, times(1)).getProductById(1L);
        verify(stabilitiesMatrixRepository, times(1)).save(any(StabilitiesMatrix.class));
    }

    @Test
    void createStabilitiesMatrix_ProductNotFound() {
        when(productService.getProductById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> stabilitiesMatrixService.createStabilitiesMatrix(stabilitiesMatrixDTO));

        assertEquals("El producto con ID 1 no existe", exception.getMessage());

        verify(productService, times(1)).getProductById(1L);
        verify(stabilitiesMatrixRepository, never()).save(any(StabilitiesMatrix.class));
    }


}