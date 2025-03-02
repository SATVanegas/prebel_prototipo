package com.prebel.prototipo.webapp.services.laboratory_reports_services.test_services;

import com.prebel.prototipo.webapp.dtos.request.laboratory_reports_requests.test_request.TestDTO;
import com.prebel.prototipo.webapp.models.laboratory_reports.Product;
import com.prebel.prototipo.webapp.models.laboratory_reports.tests.Condition;
import com.prebel.prototipo.webapp.models.laboratory_reports.tests.Storage;
import com.prebel.prototipo.webapp.models.laboratory_reports.tests.Temperature;
import com.prebel.prototipo.webapp.models.laboratory_reports.tests.Test;
import com.prebel.prototipo.webapp.models.role_module.User;
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

    public void createTest(@Valid TestDTO dto) {
        // Validar entidades relacionadas
        Product product = productService.getProductById(dto.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("El producto con ID " + dto.getProductId() + " no existe"));

        Temperature temperature = temperatureService.getTemperatureById(dto.getTemperatureId())
                .orElseThrow(() -> new EntityNotFoundException("La temperatura con ID " + dto.getTemperatureId() + " no existe"));

        Storage storage = storageService.getStorageById(dto.getStorageId())
                .orElseThrow(() -> new EntityNotFoundException("El almacenamiento con ID " + dto.getStorageId() + " no existe"));

        User userOrganolepticTests = userService.getUserById(dto.getUserOrganolepticTestsId())
                .orElseThrow(() -> new EntityNotFoundException("El usuario de pruebas organolépticas con ID " + dto.getUserOrganolepticTestsId() + " no existe"));

        User userPhysicochemicalTests = userService.getUserById(dto.getUserPhysicochemicalTestsId())
                .orElseThrow(() -> new EntityNotFoundException("El usuario de pruebas fisicoquímicas con ID " + dto.getUserPhysicochemicalTestsId() + " no existe"));

        // Obtener condiciones validadas
        List<Condition> conditions = conditionService.getConditionsFromDTO(dto);

        // Crear Test
        Test test = new Test(dto, conditions, product, temperature, storage, userOrganolepticTests, userPhysicochemicalTests);

        // Guardar en base de datos
        testRepository.save(test);
    }
}

