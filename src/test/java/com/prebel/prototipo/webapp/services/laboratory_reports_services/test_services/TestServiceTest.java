package com.prebel.prototipo.webapp.services.laboratory_reports_services.test_services;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

import java.util.List;
import java.util.Optional;

import com.prebel.prototipo.webapp.dtos.validations.laboratory_reports_requests.test_request.TestDTO;
import com.prebel.prototipo.webapp.models.laboratory_reports.Product;
import com.prebel.prototipo.webapp.models.role_module.User;
import com.prebel.prototipo.webapp.repositories.laboratory_reports_repositories.test_repositories.TestRepository;
import com.prebel.prototipo.webapp.services.laboratory_reports_services.ProductService;
import com.prebel.prototipo.webapp.services.role_module_services.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.prebel.prototipo.webapp.models.laboratory_reports.tests.Condition;
import com.prebel.prototipo.webapp.models.laboratory_reports.tests.Temperature;
import com.prebel.prototipo.webapp.models.laboratory_reports.tests.Storage;
import com.prebel.prototipo.webapp.services.laboratory_reports_services.test_services.TestService;
import com.prebel.prototipo.webapp.services.laboratory_reports_services.test_services.TemperatureService;
import com.prebel.prototipo.webapp.services.laboratory_reports_services.test_services.StorageService;
import com.prebel.prototipo.webapp.services.laboratory_reports_services.test_services.ConditionService;


@ExtendWith(MockitoExtension.class)
class TestServiceTest {

    @Mock
    private TestRepository testRepository;

    @Mock
    private ProductService productService;

    @Mock
    private TemperatureService temperatureService;

    @Mock
    private StorageService storageService;

    @Mock
    private UserService userService;

    @Mock
    private ConditionService conditionService;

    @InjectMocks
    private TestService testService;

    private TestDTO testDTO;
    private Product product;
    private Temperature temperature;
    private Storage storage;
    private User userOrganoleptic;
    private User userPhysicochemical;
    private List<Condition> conditions;

    @BeforeEach
    void setUp() {
        testDTO = new TestDTO();
        testDTO.setProductId(1L);
        testDTO.setTemperatureId(2L);
        testDTO.setStorageId(3L);

        product = new Product();
        temperature = new Temperature();
        storage = new Storage();
        userOrganoleptic = new User();
        userPhysicochemical = new User();
        conditions = List.of(new Condition());
    }

    @Test
    void testGetTestById() {
        // Configurar el mock
        com.prebel.prototipo.webapp.models.laboratory_reports.tests.Test test =
                new com.prebel.prototipo.webapp.models.laboratory_reports.tests.Test(); // Usa el nombre completo
        when(testRepository.findById(1L)).thenReturn(Optional.of(test));

        // Ejecutar el método
        Optional<com.prebel.prototipo.webapp.models.laboratory_reports.tests.Test> result =
                testService.getTestById(1L);

        // Verificar el resultado
        assertTrue(result.isPresent());
        assertEquals(test, result.get());
        verify(testRepository).findById(1L);
    }

    @Test
    void testGetTestById_NotFound() {
        // Configurar el mock
        when(testRepository.findById(1L)).thenReturn(Optional.empty());

        // Ejecutar el método
        Optional<com.prebel.prototipo.webapp.models.laboratory_reports.tests.Test> result =
                testService.getTestById(1L);

        // Verificar el resultado
        assertFalse(result.isPresent());
        verify(testRepository).findById(1L);
    }

    @Test
    void testCreateTest() {
        // Configurar los mocks
        when(productService.getProductById(1L)).thenReturn(Optional.of(product));
        when(temperatureService.getTemperatureById(2L)).thenReturn(Optional.of(temperature));
        when(storageService.getStorageById(3L)).thenReturn(Optional.of(storage));
        when(userService.getUserById(4L)).thenReturn(Optional.of(userOrganoleptic));
        when(userService.getUserById(5L)).thenReturn(Optional.of(userPhysicochemical));

        // Ejecutar el método
        testService.createTest(testDTO);

        // Verificar que se llamó al método save
        verify(testRepository).save(any(com.prebel.prototipo.webapp.models.laboratory_reports.tests.Test.class));
    }

    @Test
    void testCreateTest_ProductNotFound() {
        // Configurar el mock
        when(productService.getProductById(1L)).thenReturn(Optional.empty());

        // Ejecutar y verificar la excepción
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> testService.createTest(testDTO));
        assertEquals("El producto con ID 1 no existe", exception.getMessage());

        // Verificar que no se llamó al método save
        verify(testRepository, never()).save(any(com.prebel.prototipo.webapp.models.laboratory_reports.tests.Test.class));
    }

    @Test
    void testCreateTest_TemperatureNotFound() {
        // Configurar los mocks
        when(productService.getProductById(1L)).thenReturn(Optional.of(product));
        when(temperatureService.getTemperatureById(2L)).thenReturn(Optional.empty());

        // Ejecutar y verificar la excepción
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> testService.createTest(testDTO));
        assertEquals("La temperatura con ID 2 no existe", exception.getMessage());

        // Verificar que no se llamó al método save
        verify(testRepository, never()).save(any(com.prebel.prototipo.webapp.models.laboratory_reports.tests.Test.class));
    }

    @Test
    void testCreateTest_StorageNotFound() {
        // Configurar los mocks
        when(productService.getProductById(1L)).thenReturn(Optional.of(product));
        when(temperatureService.getTemperatureById(2L)).thenReturn(Optional.of(temperature));
        when(storageService.getStorageById(3L)).thenReturn(Optional.empty());

        // Ejecutar y verificar la excepción
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> testService.createTest(testDTO));
        assertEquals("El almacenamiento con ID 3 no existe", exception.getMessage());

        // Verificar que no se llamó al método save
        verify(testRepository, never()).save(any(com.prebel.prototipo.webapp.models.laboratory_reports.tests.Test.class));
    }

    @Test
    void testCreateTest_UserNotFound() {
        // Configurar los mocks
        when(productService.getProductById(1L)).thenReturn(Optional.of(product));
        when(temperatureService.getTemperatureById(2L)).thenReturn(Optional.of(temperature));
        when(storageService.getStorageById(3L)).thenReturn(Optional.of(storage));
        when(userService.getUserById(4L)).thenReturn(Optional.empty());

        // Ejecutar y verificar la excepción
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> testService.createTest(testDTO));
        assertEquals("El usuario de pruebas organolépticas con ID 4 no existe", exception.getMessage());

        // Verificar que no se llamó al método save
        verify(testRepository, never()).save(any(com.prebel.prototipo.webapp.models.laboratory_reports.tests.Test.class));
    }

}