package com.prebel.prototipo.webapp.controllers.laboratory_reports_controllers.tests_controllers;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.prebel.prototipo.webapp.models.laboratory_reports.tests.Condition;
import com.prebel.prototipo.webapp.dtos.validations.laboratory_reports_requests.test_request.TestDTO;
import com.prebel.prototipo.webapp.models.laboratory_reports.Product;
import com.prebel.prototipo.webapp.models.laboratory_reports.tests.Storage;
import com.prebel.prototipo.webapp.models.laboratory_reports.tests.Temperature;
import com.prebel.prototipo.webapp.models.role_module.User;
import com.prebel.prototipo.webapp.repositories.laboratory_reports_repositories.ProductRepository;
import com.prebel.prototipo.webapp.repositories.laboratory_reports_repositories.test_repositories.ConditionRepository;
import com.prebel.prototipo.webapp.repositories.laboratory_reports_repositories.test_repositories.StorageRepository;
import com.prebel.prototipo.webapp.repositories.laboratory_reports_repositories.test_repositories.TemperatureRepository;
import com.prebel.prototipo.webapp.repositories.laboratory_reports_repositories.test_repositories.TestRepository;
import com.prebel.prototipo.webapp.repositories.role_module_repositories.UserRepository;
import jakarta.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestControllerTest {

    private static final String BASE_URL = "/api/test";

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TemperatureRepository temperatureRepository;

    @Autowired
    private StorageRepository storageRepository;

    @Autowired
    private ConditionRepository conditionRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void cuandoSeBuscaPorIdDebeRetornarTest() throws Exception {
        // Crear y guardar las entidades relacionadas
        Product product = new Product();
        productRepository.save(product);

        Temperature temperature = new Temperature();
        temperatureRepository.save(temperature);

        Storage storage = new Storage();
        storageRepository.save(storage);

        Condition condition1 = new Condition();
        Condition condition2 = new Condition();
        conditionRepository.saveAll(List.of(condition1, condition2));

        User userOrganolepticTests = new User();
        userRepository.save(userOrganolepticTests);

        User userPhysicochemicalTests = new User();
        userRepository.save(userPhysicochemicalTests);

        // Crear y guardar el Test
        com.prebel.prototipo.webapp.models.laboratory_reports.tests.Test test =
                new com.prebel.prototipo.webapp.models.laboratory_reports.tests.Test(
                        new TestDTO(), List.of(condition1, condition2), product, temperature, storage,
                        userOrganolepticTests, userPhysicochemicalTests);

        test = testRepository.save(test);

        // Ejecutar el GET y verificar la respuesta
        mockMvc.perform(get(BASE_URL + "/" + test.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.product").exists())  // Verifica que el objeto existe
                .andExpect(jsonPath("$.temperature").exists())
                .andExpect(jsonPath("$.storage").exists())
                .andExpect(jsonPath("$.conditions").isArray())
                .andExpect(jsonPath("$.conditions.length()").value(2))  // Deben existir 2 condiciones
                .andExpect(jsonPath("$.userOrganolepticTests").exists())
                .andExpect(jsonPath("$.userPhysicochemicalTests").exists())
                .andExpect(jsonPath("$.observations").value("Observación de prueba"))
                .andExpect(jsonPath("$.conclusion").value("Conclusión de prueba"));
    }


    @Test
    void cuandoSeBuscaPorIdYNoExisteDebeRetornar404() throws Exception {
        mockMvc.perform(get(BASE_URL + "/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void cuandoSeCreaTestDebeRetornar200() throws Exception {
        TestDTO dto = new TestDTO();
        dto.setPhId(1L);
        dto.setViscosityId(2L);
        dto.setSpecificGravityId(3L);
        dto.setTotalBacteriaCountId(4L);
        dto.setFungiYeastCountId(5L);
        dto.setPathogensId(6L);
        dto.setStorageId(7L);
        dto.setUserOrganolepticTestsId(8L);
        dto.setUserPhysicochemicalTestsId(9L);
        dto.setObservations("Observación de prueba");
        dto.setConclusion("Conclusión de prueba");

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Test creado correctamente"));
    }

    @Test
    void cuandoSeCreaTestConDatosInvalidosDebeRetornar400() throws Exception {
        TestDTO dto = new TestDTO(); // DTO vacío, provocará errores de validación

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors.length()").value(14));
    }
}
