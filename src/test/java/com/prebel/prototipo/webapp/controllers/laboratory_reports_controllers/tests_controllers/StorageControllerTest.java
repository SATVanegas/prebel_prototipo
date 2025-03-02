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

import com.prebel.prototipo.webapp.dtos.request.laboratory_reports_requests.test_request.TestStorageDTO;
import com.prebel.prototipo.webapp.models.laboratory_reports.tests.Storage;
import com.prebel.prototipo.webapp.repositories.laboratory_reports_repositories.test_repositories.StorageRepository;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class StorageControllerTest {

    private static final String BASE_URL = "/api/test/storage";

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private StorageRepository storageRepository;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void cuandoSeBuscaPorIdDebeRetornarStorage() throws Exception {
        List<Object> testData = crearStorageYDTODePrueba();
        Storage storage = (Storage) testData.getFirst();

        // Ejecutar el GET y verificar la respuesta
        mockMvc.perform(get(BASE_URL + "/" + storage.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.maxTemperature").value(10))
                .andExpect(jsonPath("$.minTemperature").value(-5))
                .andExpect(jsonPath("$.equipmentCode").value("EQ123"));
    }

    @Test
    void cuandoSeBuscaPorIdYNoExisteDebeRetornar404() throws Exception {
        mockMvc.perform(get(BASE_URL + "/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void cuandoSeCreaStorageDebeRetornar200() throws Exception {
        List<Object> testData = crearStorageYDTODePrueba();
        TestStorageDTO dto = (TestStorageDTO) testData.get(1);

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Test creado correctamente"));
    }

    @Test
    void createStorage_Returns400_WhenInvalid() throws Exception {
        TestStorageDTO dto = new TestStorageDTO();

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors.length()").value(3));
    }

    private List<Object> crearStorageYDTODePrueba() {
        // Crear DTO de prueba
        TestStorageDTO dto = new TestStorageDTO();
        dto.setMaxTemperature(10);
        dto.setMinTemperature(-5);
        dto.setEquipmentCode("EQ123");
        dto.setDescription("Test Storage");

        // Crear y guardar la entidad Storage
        Storage storage = new Storage(dto);
        storage = storageRepository.save(storage);

        return Arrays.asList(storage, dto);

    }
}
