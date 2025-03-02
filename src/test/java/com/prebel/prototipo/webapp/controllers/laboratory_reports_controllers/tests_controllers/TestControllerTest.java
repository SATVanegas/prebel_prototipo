package com.prebel.prototipo.webapp.controllers.laboratory_reports_controllers.tests_controllers;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.prebel.prototipo.webapp.repositories.laboratory_reports_repositories.ProductRepository;
import com.prebel.prototipo.webapp.repositories.laboratory_reports_repositories.test_repositories.ConditionRepository;
import com.prebel.prototipo.webapp.repositories.laboratory_reports_repositories.test_repositories.StorageRepository;
import com.prebel.prototipo.webapp.repositories.laboratory_reports_repositories.test_repositories.TemperatureRepository;
import com.prebel.prototipo.webapp.repositories.laboratory_reports_repositories.test_repositories.TestRepository;
import com.prebel.prototipo.webapp.repositories.role_module_repositories.UserRepository;
import com.prebel.prototipo.webapp.models.laboratory_reports.tests.Condition;
import com.prebel.prototipo.webapp.dtos.validations.laboratory_reports_requests.test_request.TestDTO;
import com.prebel.prototipo.webapp.models.laboratory_reports.Product;
import com.prebel.prototipo.webapp.models.laboratory_reports.tests.Storage;
import com.prebel.prototipo.webapp.models.laboratory_reports.tests.Temperature;
import com.prebel.prototipo.webapp.models.role_module.User;
import com.prebel.prototipo.webapp.models.laboratory_reports.EnumTest;

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
        List<Object> resultado = crearTestYDto();
        com.prebel.prototipo.webapp.models.laboratory_reports.tests.Test test = (com.prebel.prototipo.webapp.models.laboratory_reports.tests.Test) resultado.getFirst();

        test = testRepository.save(test);

        mockMvc.perform(get(BASE_URL + "/" + test.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.product").exists())  // Verifica que el objeto existe
                .andExpect(jsonPath("$.temperature").exists())
                .andExpect(jsonPath("$.storage").exists())
                .andExpect(jsonPath("$.conditions").isArray())
                .andExpect(jsonPath("$.conditions.length()").value(9))  // Verifica que hay 9 condiciones
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
        // Obtener el objeto Test y su DTO
        List<Object> testData = crearTestYDto();
        TestDTO dto = (TestDTO) testData.get(1);

        // Ejecutar la solicitud POST
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

    private List<Object> crearTestYDto() {
        // Crear y guardar entidades relacionadas
        Product product = productRepository.save(new Product());
        Temperature temperature = temperatureRepository.save(new Temperature());
        Storage storage = storageRepository.save(new Storage());
        User userOrganolepticTests = userRepository.save(new User());
        User userPhysicochemicalTests = userRepository.save(new User());

        // Crear y guardar condiciones con un método auxiliar
        List<Condition> conditions = (List<Condition>) conditionRepository.saveAll(List.of(
                crearCondition(EnumTest.COLOR),
                crearCondition(EnumTest.ODOR),
                crearCondition(EnumTest.APPEARANCE),
                crearCondition(EnumTest.PH),
                crearCondition(EnumTest.VISCOSITY),
                crearCondition(EnumTest.SPECIFIC_GRAVITY),
                crearCondition(EnumTest.TOTAL_BACTERIA_COUNT),
                crearCondition(EnumTest.FUNGI_YEAST_COUNT),
                crearCondition(EnumTest.PATHOGENS)
        ));

        // Asignar IDs de condiciones al DTO
        TestDTO dto = new TestDTO();
        dto.setProductId(product.getId());
        dto.setTemperatureId(temperature.getId());
        dto.setStorageId(storage.getId());
        dto.setUserOrganolepticTestsId(userOrganolepticTests.getId());
        dto.setUserPhysicochemicalTestsId(userPhysicochemicalTests.getId());

        dto.setColorId(conditions.get(0).getId());
        dto.setOdorId(conditions.get(1).getId());
        dto.setAppearanceId(conditions.get(2).getId());
        dto.setPhId(conditions.get(3).getId());
        dto.setViscosityId(conditions.get(4).getId());
        dto.setSpecificGravityId(conditions.get(5).getId());
        dto.setTotalBacteriaCountId(conditions.get(6).getId());
        dto.setFungiYeastCountId(conditions.get(7).getId());
        dto.setPathogensId(conditions.get(8).getId());

        dto.setObservations("Observación de prueba");
        dto.setConclusion("Conclusión de prueba");

        // Crear el objeto Test a partir del DTO y las entidades
        com.prebel.prototipo.webapp.models.laboratory_reports.tests.Test test =
                new com.prebel.prototipo.webapp.models.laboratory_reports.tests.Test(
                        dto, conditions, product, temperature, storage);

        return List.of(test, dto);
    }

    private Condition crearCondition(EnumTest tipo) {
        Condition condition = new Condition();
        condition.setType(tipo);
        return condition;
    }


}
