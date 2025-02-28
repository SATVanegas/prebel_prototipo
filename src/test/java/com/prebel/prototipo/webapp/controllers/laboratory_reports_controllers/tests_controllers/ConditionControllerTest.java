package com.prebel.prototipo.webapp.controllers.laboratory_reports_controllers.tests_controllers;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.prebel.prototipo.webapp.dtos.validations.laboratory_reports_requests.test_request.TestConditionDTO;
import com.prebel.prototipo.webapp.models.laboratory_reports.EnumTest;
import com.prebel.prototipo.webapp.models.laboratory_reports.tests.Condition;
import com.prebel.prototipo.webapp.repositories.laboratory_reports_repositories.test_repositories.ConditionRepository;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ConditionControllerTest {

    private static final String BASE_URL = "/api/test/conditions";

    private MockMvc mockMvc;

    @Autowired
    private Environment environment;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ConditionRepository conditionRepository;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void cuandoSeBuscaPorIdDebeRetornarCondition() throws Exception {
        List<Object> testData = crearConditionYDTODePrueba();
        Condition condition = (Condition) testData.getFirst();

        // Ejecutar el GET y verificar la respuesta
        mockMvc.perform(get(BASE_URL +"/" +condition.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type").value("COLOR"))
                .andExpect(jsonPath("$.unit").value("Celsius"))
                .andExpect(jsonPath("$.time").value(12));
    }

    @Test
    void cuandoSeBuscaPorIdYNoExisteDebeRetornar404() throws Exception {
        mockMvc.perform(get(BASE_URL + "/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void cuandoSeCreaConditionDebeRetornar200() throws Exception {
        List<Object> testData = crearConditionYDTODePrueba();
        TestConditionDTO dto = (TestConditionDTO) testData.get(1);

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void cuandoSeCreaConditionConDatosInvalidosDebeRetornar400() throws Exception {
        TestConditionDTO dto = new TestConditionDTO();

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors.length()").value(5));
    }


    @Test
    void imprimirTodasLasPropiedadesDeSpring() {
        System.out.println("==== PROPIEDADES CARGADAS POR SPRING ====");
        System.out.println("Base de datos usada en pruebas: " + environment.getProperty("spring.datasource.url"));
        System.out.println("Usuario: " + environment.getProperty("spring.datasource.username"));
        System.out.println("Contraseña: " + environment.getProperty("spring.datasource.password"));
    }

    private List<Object> crearConditionYDTODePrueba() {
        // Crear DTO de prueba
        TestConditionDTO dto = new TestConditionDTO();
        dto.setType(EnumTest.COLOR);
        dto.setUnit("Celsius");
        dto.setTime(12);
        dto.setEquipment(1);
        dto.setMethod("Método A");

        // Crear y guardar la entidad Condition
        Condition condition = new Condition(dto);
        condition = conditionRepository.save(condition);

        return Arrays.asList(condition, dto);

    }
}
