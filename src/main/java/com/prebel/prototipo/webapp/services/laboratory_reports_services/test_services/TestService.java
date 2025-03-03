package com.prebel.prototipo.webapp.services.laboratory_reports_services.test_services;

import com.prebel.prototipo.webapp.dtos.validations.laboratory_reports_requests.test_request.TestDTO;
import com.prebel.prototipo.webapp.models.laboratory_reports.Product;
import com.prebel.prototipo.webapp.models.laboratory_reports.tests.Condition;
import com.prebel.prototipo.webapp.models.laboratory_reports.tests.Storage;
import com.prebel.prototipo.webapp.models.laboratory_reports.tests.Temperature;
import com.prebel.prototipo.webapp.models.laboratory_reports.tests.Test;
import com.prebel.prototipo.webapp.repositories.laboratory_reports_repositories.test_repositories.TestRepository;
import com.prebel.prototipo.webapp.services.laboratory_reports_services.ProductService;
import com.prebel.prototipo.webapp.services.role_module_services.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TestService {

    private final TestRepository testRepository;
    private final ProductService productService;
    private final TemperatureService temperatureService;
    private final StorageService storageService;
    private final UserService userService;
    private final ConditionService conditionService;

    public TestService(TestRepository testRepository, ProductService productService,
                       TemperatureService temperatureService, StorageService storageService,
                       UserService userService, ConditionService conditionService) {
        this.testRepository = testRepository;
        this.productService = productService;
        this.temperatureService = temperatureService;
        this.storageService = storageService;
        this.userService = userService;
        this.conditionService = conditionService;
    }

    public Optional<Test> getTestById(Long id) {
        return testRepository.findById(id);
    }

    public Test createTest(@Valid TestDTO dto) {
        // Validar entidades relacionadas
        Product product = productService.getProductById(dto.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("El producto con ID " + dto.getProductId() + " no existe"));

        Temperature temperature = temperatureService.getTemperatureById(dto.getTemperatureId())
                .orElseThrow(() -> new EntityNotFoundException("La temperatura con ID " + dto.getTemperatureId() + " no existe"));

        Storage storage = storageService.getStorageById(dto.getStorageId())
                .orElseThrow(() -> new EntityNotFoundException("El almacenamiento con ID " + dto.getStorageId() + " no existe"));

        // Crear Test
        Test test = new Test(dto, product, temperature, storage);

        // Guardar en base de datos
        testRepository.save(test);
        return test;
    }

    public Optional<TestDTO> getTestDTOById(Long id) {
        Optional<Test> testOpt = testRepository.findById(id);
        if (testOpt.isEmpty()) {
            System.out.println("Test not found with id " + id);
            return Optional.empty();
        }
        Test test = testOpt.get();
        return Optional.of(convertToDTO(test));
    }

    private TestDTO convertToDTO(Test test) {
        TestDTO dto = new TestDTO();
        dto.setProductId(test.getProduct().getId());
        dto.setTemperatureId(test.getTemperature().getId());
        dto.setStorageId(test.getStorage().getId());
        return dto;
    }
}

