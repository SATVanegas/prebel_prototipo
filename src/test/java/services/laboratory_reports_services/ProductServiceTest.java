package services.laboratory_reports_services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import com.prebel.prototipo.webapp.dtos.validations.laboratory_reports_requests.ProductDTO;
import com.prebel.prototipo.webapp.models.laboratory_reports.Product;
import com.prebel.prototipo.webapp.models.role_module.User;
import com.prebel.prototipo.webapp.repositories.laboratory_reports_repositories.ProductRepository;
import com.prebel.prototipo.webapp.services.laboratory_reports_services.ProductService;
import com.prebel.prototipo.webapp.services.role_module_services.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserService userService;

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
    void getProductById_Success() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Optional<Product> result = productService.getProductById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getCustomer().getId());
        assertEquals(2L, result.get().getResponsibleChemist().getId());
        assertEquals(3L, result.get().getResponsibleEngineer().getId());
        assertEquals(4L, result.get().getResponsibleAnalyst().getId());
        assertEquals(5L, result.get().getTechnicianInCharge().getId());

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

}