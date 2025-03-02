package com.prebel.prototipo.webapp.controllers.laboratory_reports_controllers;

import java.util.Arrays;
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

import com.prebel.prototipo.webapp.dtos.request.laboratory_reports_requests.StabilitiesMatrixDTO;
import com.prebel.prototipo.webapp.models.laboratory_reports.StabilitiesMatrix;
import com.prebel.prototipo.webapp.models.laboratory_reports.Product;
import com.prebel.prototipo.webapp.models.role_module.User;
import com.prebel.prototipo.webapp.repositories.laboratory_reports_repositories.ProductRepository;
import com.prebel.prototipo.webapp.repositories.role_module_repositories.UserRepository;
import com.prebel.prototipo.webapp.repositories.laboratory_reports_repositories.StabilitiesMatrixRepository;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class StabilitiesMatrixControllerTest {

    private static final String BASE_URL = "/stabilities-matrix";

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private StabilitiesMatrixRepository stabilitiesMatrixRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void cuandoSeBuscaPorIdDebeRetornarStabilitiesMatrix() throws Exception {
        List<Object> datos = crearStabilitiesMatrixYDTODePrueba();
        StabilitiesMatrix stabilitiesMatrix = (StabilitiesMatrix) datos.getFirst();

        mockMvc.perform(get(BASE_URL + "/" + stabilitiesMatrix.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.projectCode").value(stabilitiesMatrix.getProjectCode()))
                .andExpect(jsonPath("$.formulaCode").value(stabilitiesMatrix.getFormulaCode()))
                .andExpect(jsonPath("$.product.id").value(stabilitiesMatrix.getProduct().getId()));
    }

    @Test
    void cuandoSeBuscaPorIdYNoExisteDebeRetornar404() throws Exception {
        mockMvc.perform(get(BASE_URL + "/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void cuandoSeCreaStabilitiesMatrixDebeRetornar200() throws Exception {
        List<Object> testData = crearStabilitiesMatrixYDTODePrueba();
        StabilitiesMatrixDTO dto = (StabilitiesMatrixDTO) testData.get(1);

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Test creado correctamente"));
    }

    @Test
    void cuandoSeCreaStabilitiesMatrixConDatosInvalidosDebeRetornar400() throws Exception {
        StabilitiesMatrixDTO dto = new StabilitiesMatrixDTO();

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors.length()").value(6));
    }

    private List<Object> crearStabilitiesMatrixYDTODePrueba() {
        // Crear y guardar objetos relacionados
        User customer = userRepository.save(new User());
        User chemical = userRepository.save(new User());
        User engineer = userRepository.save(new User());
        Product product = productRepository.save(new Product());

        // Crear DTO de prueba
        StabilitiesMatrixDTO dto = new StabilitiesMatrixDTO();
        dto.setCustomerId(customer.getId());
        dto.setChemicalId(chemical.getId());
        dto.setEngineerId(engineer.getId());
        dto.setProductId(product.getId());
        dto.setProjectCode("PROJ123");
        dto.setFormulaCode("FORM001");

        // Crear y guardar la entidad StabilitiesMatrix
        StabilitiesMatrix stabilitiesMatrix = new StabilitiesMatrix(dto, product);
        stabilitiesMatrix = stabilitiesMatrixRepository.save(stabilitiesMatrix);

        return Arrays.asList(stabilitiesMatrix, dto);
    }
}
