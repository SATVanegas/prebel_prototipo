package com.prebel.prototipo.webapp.controllers.laboratory_reports_controllers.tests_controllers;

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

import com.prebel.prototipo.webapp.dtos.validations.laboratory_reports_requests.test_request.TestTemperatureDTO;
import com.prebel.prototipo.webapp.models.laboratory_reports.tests.Temperature;
import com.prebel.prototipo.webapp.repositories.laboratory_reports_repositories.test_repositories.TemperatureRepository;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TemperatureControllerTest {

    private static final String BASE_URL = "/api/test/temperature";

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TemperatureRepository temperatureRepository;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void cuandoSeBuscaPorIdDebeRetornarTemperature() throws Exception {
        List<Object> testData = crearTemperatureYDTODePrueba();
        Temperature temperature = (Temperature) testData.getFirst();

        // Ejecutar el GET y verificar la respuesta
        mockMvc.perform(get(BASE_URL + "/" + temperature.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.unit").value("Celsius"))
                .andExpect(jsonPath("$.time").value(12))
                .andExpect(jsonPath("$.equipment").value(1));
    }

    @Test
    void cuandoSeBuscaPorIdYNoExisteDebeRetornar404() throws Exception {
        mockMvc.perform(get(BASE_URL + "/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void cuandoSeCreaTemperatureDebeRetornar200() throws Exception {
        List<Object> testData = crearTemperatureYDTODePrueba();
        TestTemperatureDTO dto = (TestTemperatureDTO) testData.get(1);

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Test creado correctamente"));
    }

    @Test
    void cuandoSeCreaTemperatureConDatosInvalidosDebeRetornar400() throws Exception {
        TestTemperatureDTO dto = new TestTemperatureDTO();

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors.length()").value(3));
    }

    private List<Object> crearTemperatureYDTODePrueba() {
        // Crear DTO de prueba
        TestTemperatureDTO dto = new TestTemperatureDTO();
        dto.setUnit("Celsius");
        dto.setTime(12);
        dto.setEquipment(1);

        // Crear y guardar la entidad temperature
        Temperature temperature = new Temperature(dto);
        temperature = temperatureRepository.save(temperature);

        return Arrays.asList(temperature, dto);

    }
}
