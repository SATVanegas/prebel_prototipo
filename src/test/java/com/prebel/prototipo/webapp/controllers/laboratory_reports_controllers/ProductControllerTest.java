package com.prebel.prototipo.webapp.controllers.laboratory_reports_controllers;

import java.util.Date;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.prebel.prototipo.webapp.dtos.validations.laboratory_reports_requests.ProductDTO;
import com.prebel.prototipo.webapp.models.role_module.User;
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

import com.prebel.prototipo.webapp.repositories.role_module_repositories.UserRepository;
import com.prebel.prototipo.webapp.repositories.laboratory_reports_repositories.ProductRepository;
import com.prebel.prototipo.webapp.models.laboratory_reports.Product;
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProductControllerTest {

    private static final String BASE_URL = "/api/products";

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void cuandoSeBuscaPorIdDebeRetornarProduct() throws Exception {
        // Crear y guardar un Product de prueba
        List<Object> datos = crearProductYDTODePrueba();
        Product product = (Product) datos.getFirst();

        // Ejecutar el GET y verificar la respuesta con los valores del Product guardado
        mockMvc.perform(get(BASE_URL + "/" + product.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reference").value(product.getReference()))
                .andExpect(jsonPath("$.batch").value(product.getBatch()))
                .andExpect(jsonPath("$.packagingType").value(product.getPackagingType()))
                .andExpect(jsonPath("$.packagingMaterial").value(product.getPackagingMaterial()))
                .andExpect(jsonPath("$.consecutive").value(product.getConsecutive()))
                .andExpect(jsonPath("$.studyDuration").value(product.getStudyDuration()));
    }

    @Test
    void cuandoSeBuscaPorIdYNoExisteDebeRetornar404() throws Exception {
        mockMvc.perform(get(BASE_URL + "/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void cuandoSeCreaProductDebeRetornar200() throws Exception {
        List<Object> testData = crearProductYDTODePrueba();
        ProductDTO dto = (ProductDTO) testData.get(1);

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Product creado correctamente"));
    }

    @Test
    void createProduct_Returns400_WhenInvalid() throws Exception {
        ProductDTO dto = new ProductDTO();

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors.length()").value(11));
    }

    private List<Object> crearProductYDTODePrueba() {
        // Crear y guardar objetos relacionados
        User customer = userRepository.save(new User());
        User chemist = userRepository.save(new User());
        User engineer = userRepository.save(new User());
        User analyst = userRepository.save(new User());
        User technician = userRepository.save(new User());

        // Crear DTO de prueba
        ProductDTO dto = new ProductDTO();
        dto.setCustomerId(customer.getId());
        dto.setResponsibleChemistId(chemist.getId());
        dto.setResponsibleEngineerId(engineer.getId());
        dto.setResponsibleAnalystId(analyst.getId());
        dto.setTechnicianInChargeId(technician.getId());
        dto.setReference("REF123");
        dto.setBatch("BATCH001");
        dto.setPackagingType("Caja de cartón");
        dto.setPackagingMaterial("Cartón");
        dto.setConsecutive(1);
        dto.setStudyDuration(12);
        dto.setStartDate(new Date());
        dto.setFinishDate(new Date());

        // Crear y guardar la entidad Product
        Product product = new Product(dto, customer, chemist, engineer, analyst, technician);
        product = productRepository.save(product);

        return Arrays.asList(product, dto);
    }

}
