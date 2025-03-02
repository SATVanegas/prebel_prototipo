package com.prebel.prototipo.webapp.controllers.laboratory_reports_controllers;

import java.util.Arrays;
import java.util.Date;
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

import com.prebel.prototipo.webapp.repositories.laboratory_reports_repositories.StabilitiesMatrixRepository;
import com.prebel.prototipo.webapp.repositories.laboratory_reports_repositories.test_repositories.TestRepository;
import com.prebel.prototipo.webapp.repositories.laboratory_reports_repositories.InspectionRepository;
import com.prebel.prototipo.webapp.dtos.request.laboratory_reports_requests.InspectionDTO;
import com.prebel.prototipo.webapp.models.laboratory_reports.StabilitiesMatrix;
import com.prebel.prototipo.webapp.models.laboratory_reports.Inspection;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class InspectionControllerTest {

    private static final String BASE_URL = "/inspections";

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private InspectionRepository inspectionRepository;

    @Autowired
    private StabilitiesMatrixRepository stabilitiesMatrixRepository;

    @Autowired
    private TestRepository testRepository;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void cuandoSeBuscaPorIdDebeRetornarInspection() throws Exception {
        // Crear datos de prueba
        List<Object> datos = crearInspectionDePrueba();
        Inspection inspection = (Inspection) datos.getFirst();

        // Ejecutar el GET y verificar la respuesta
        mockMvc.perform(get(BASE_URL + "/" + inspection.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.responseTime").value(inspection.getResponseTime()))
                .andExpect(jsonPath("$.aerosolStove").value(inspection.getAerosolStove()))
                .andExpect(jsonPath("$.inOut").value(inspection.getInOut()))
                .andExpect(jsonPath("$.stove").value(inspection.getStove()))
                .andExpect(jsonPath("$.hrStove").value(inspection.getHrStove()))
                .andExpect(jsonPath("$.environment").value(inspection.getEnvironment()))
                .andExpect(jsonPath("$.fridge").value(inspection.getFridge()))
                .andExpect(jsonPath("$.photolysis").value(inspection.getPhotolysis()));
    }

    @Test
    void cuandoSeBuscaPorIdYNoExisteDebeRetornar404() throws Exception {
        mockMvc.perform(get(BASE_URL + "/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void cuandoSeCreaInspectionDebeRetornar200() throws Exception {
        List<Object> datos = crearInspectionDePrueba();
        InspectionDTO dto = (InspectionDTO) datos.get(1);

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Inspection creada correctamente"));
    }

    @Test
    void cuandoSeCreaInspectionConDatosInvalidosDebeRetornar400() throws Exception {
        InspectionDTO dto = new InspectionDTO();

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors.length()").value(3));
    }

    private List<Object> crearInspectionDePrueba() {
        // Crear y guardar objetos relacionados
        StabilitiesMatrix matrix = stabilitiesMatrixRepository.save(new StabilitiesMatrix());
        com.prebel.prototipo.webapp.models.laboratory_reports.tests.Test test = testRepository.save(new com.prebel.prototipo.webapp.models.laboratory_reports.tests.Test());

        // Crear DTO de prueba
        InspectionDTO dto = new InspectionDTO();
        dto.setExpectedDate(new Date());
        dto.setRealDate(new Date());
        dto.setResponseTime(5);
        dto.setAerosolStove(1);
        dto.setInOut(1);
        dto.setStove(1);
        dto.setHrStove(1);
        dto.setEnvironment(1);
        dto.setFridge(1);
        dto.setPhotolysis(1);
        dto.setStabilitiesMatrixId(matrix.getId());
        dto.setTestId(test.getId());

        // Crear y guardar la entidad Inspection
        Inspection inspection = new Inspection(dto, matrix, test);
        inspection = inspectionRepository.save(inspection);

        return Arrays.asList(inspection, dto);
    }
}
