package com.prebel.prototipo.webapp.services.utils;

import com.prebel.prototipo.webapp.models.laboratory_reports.EnumTest;
import com.prebel.prototipo.webapp.models.laboratory_reports.tests.Condition;
import com.prebel.prototipo.webapp.models.laboratory_reports.tests.Storage;
import com.prebel.prototipo.webapp.models.laboratory_reports.tests.Temperature;
import com.prebel.prototipo.webapp.models.laboratory_reports.Product;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class PdfReportServiceTest {

    private PdfReportService pdfReportService;

    @BeforeEach
    void setUp() {
        pdfReportService = new PdfReportService();
    }

    @Test
    void cuandoSeGeneraElPdfDebeContenerLaInformacionCorrecta() throws IOException {
        // Crear un producto de prueba con tests
        Product product = crearProductoDePrueba();

        // Generar el PDF
        byte[] pdfBytes = pdfReportService.createProductReport(product);

        // Verificar que el PDF no está vacío
        assertNotNull(pdfBytes, "El PDF no se generó correctamente (bytes null)");
        assertTrue(pdfBytes.length > 0, "El PDF generado está vacío");

        // Leer el contenido del PDF usando PDFBox
        try (PDDocument document = PDDocument.load(new ByteArrayInputStream(pdfBytes))) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            String pdfText = pdfStripper.getText(document);

            // ==========================
            // VALIDACIÓN DE ENCABEZADOS
            // ==========================
            verificarTextoEnPdf(pdfText, "Product description:");
            verificarTextoEnPdf(pdfText, "Reference:");
            verificarTextoEnPdf(pdfText, "Batch:");
            verificarTextoEnPdf(pdfText, "Packaging Type:");
            verificarTextoEnPdf(pdfText, "Packaging Material:");
            verificarTextoEnPdf(pdfText, "Container Color:");
            verificarTextoEnPdf(pdfText, "Lid Material:");
            verificarTextoEnPdf(pdfText, "Lid Color:");
            verificarTextoEnPdf(pdfText, "Formula number:");
            verificarTextoEnPdf(pdfText, "Project code:");
            verificarTextoEnPdf(pdfText, "Project name:");
            verificarTextoEnPdf(pdfText, "Customer:");
            verificarTextoEnPdf(pdfText, "Brand:");
            verificarTextoEnPdf(pdfText, "Study type:");
            verificarTextoEnPdf(pdfText, "Consecutive:");
            verificarTextoEnPdf(pdfText, "Justification:");
            verificarTextoEnPdf(pdfText, "Qualification:");
            verificarTextoEnPdf(pdfText, "Established validity:");
            verificarTextoEnPdf(pdfText, "Responsible Chemist:");
            verificarTextoEnPdf(pdfText, "Responsible Engineer:");
            verificarTextoEnPdf(pdfText, "Responsible Analyst:");
            verificarTextoEnPdf(pdfText, "Technician in charge:");
            verificarTextoEnPdf(pdfText, "Study duration:");
            verificarTextoEnPdf(pdfText, "Start Date:");
            verificarTextoEnPdf(pdfText, "Finish Date:");

            // ==========================
            // VALIDACIÓN DE DATOS DEL PRODUCTO
            // ==========================
            verificarTextoEnPdf(pdfText, product.getProductDescription());
            verificarTextoEnPdf(pdfText, product.getReference());
            verificarTextoEnPdf(pdfText, product.getBatch());

            // ==========================
            // VALIDACIÓN DE LA TABLA DE TESTS
            // ==========================
            verificarTextoEnPdf(pdfText, "TEST");
            verificarTextoEnPdf(pdfText, "UNIT");
            verificarTextoEnPdf(pdfText, "SPECIFICATION");
            verificarTextoEnPdf(pdfText, "METHOD");
            verificarTextoEnPdf(pdfText, "INITIAL RESULTS DEVELOPMENT LABORATORY");
            verificarTextoEnPdf(pdfText, "INITIAL RESULTS FROM STABILITY LABORATORY");

            // Validar que la tabla contiene todos los `EnumTest`
            EnumTest[] testTypes = EnumTest.values();

            for (com.prebel.prototipo.webapp.models.laboratory_reports.tests.Test test : product.getTests()) {
                List<Condition> conditions = (test.getConditions() != null) ? test.getConditions() : new ArrayList<>();

                for (EnumTest testType : testTypes) {
                    Condition condition = conditions.stream()
                            .filter(c -> c.getType() == testType)
                            .findFirst()
                            .orElse(null);

                    verificarTextoEnPdf(pdfText, testType.name());
                    verificarTextoEnPdf(pdfText, condition != null ? condition.getUnit() : "No registrado");
                    verificarTextoEnPdf(pdfText, condition != null ? condition.getSpecification() : "No registrado");
                    verificarTextoEnPdf(pdfText, condition != null ? condition.getMethod() : "No registrado");
                    verificarTextoEnPdf(pdfText, condition != null ? condition.getInitialResultsDevelopmentLaboratory() : "No registrado");
                    verificarTextoEnPdf(pdfText, condition != null ? condition.getInitialResultsStabilityLaboratory() : "No registrado");
                }
            }

            // ==========================
            // VALIDACIÓN DE STORAGE
            // ==========================
            verificarTextoEnPdf(pdfText, "STORAGE CONDITION:");
            verificarTextoEnPdf(pdfText, "STORAGE EQUIPMENT CODE");
            verificarTextoEnPdf(pdfText, "DESCRIPTION");

            if (product.getTests() != null && !product.getTests().isEmpty() && product.getTests().getFirst().getStorage() != null) {
                Storage storage = product.getTests().getFirst().getStorage();
                verificarTextoEnPdf(pdfText, storage.getEquipmentCode());
                verificarTextoEnPdf(pdfText, storage.getDescription());
            } else {
                verificarTextoEnPdf(pdfText, "No registrado");
            }

            // ==========================
            // VALIDACIÓN DE TEMPERATURE
            // ==========================
            verificarTextoEnPdf(pdfText, "TEMPERATURE / UNIT");
            verificarTextoEnPdf(pdfText, "EQUIPMENT");

            if (product.getTests() != null && !product.getTests().isEmpty() && product.getTests().getFirst().getTemperature() != null) {
                Temperature temperature = product.getTests().getFirst().getTemperature();
                verificarTextoEnPdf(pdfText, temperature.getUnit());
                verificarTextoEnPdf(pdfText, String.valueOf(temperature.getEquipment()));
            } else {
                verificarTextoEnPdf(pdfText, "No registrado");
            }
        }
    }

    /**
     * Verifica que un texto esperado esté presente en el contenido del PDF.
     */
    private void verificarTextoEnPdf(String pdfText, String textoEsperado) {
        // Normalizar el contenido del PDF eliminando saltos de línea y espacios dobles
        String pdfNormalizado = pdfText.replaceAll("\\s+", " ").trim();
        String textoEsperadoNormalizado = textoEsperado.replaceAll("\\s+", " ").trim();

        assertTrue(pdfNormalizado.contains(textoEsperadoNormalizado),
                "El texto esperado '" + textoEsperado + "' no está presente en el PDF.");
    }



    private Product crearProductoDePrueba() {
        Product product = new Product();
        product.setProductDescription("Producto de prueba");
        product.setReference("REF123");
        product.setBatch("BATCH001");
        product.setFormulaNumber("123456789");

        // Crear tests de prueba
        com.prebel.prototipo.webapp.models.laboratory_reports.tests.Test test1 =
                new com.prebel.prototipo.webapp.models.laboratory_reports.tests.Test();
        test1.setId(1L);
        test1.setObservations("Observación 1");
        test1.setConclusion("Conclusión 1");
        Temperature temp1 = new Temperature();
        temp1.setUnit("°C");
        test1.setTemperature(temp1);

        com.prebel.prototipo.webapp.models.laboratory_reports.tests.Test test2 = new com.prebel.prototipo.webapp.models.laboratory_reports.tests.Test();
        test2.setId(2L);
        test2.setObservations("Observación 2");
        test2.setConclusion("Conclusión 2");
        Temperature temp2 = new Temperature();
        temp2.setUnit("°F");
        test2.setTemperature(temp2);

        product.setTests(Arrays.asList(test1, test2));

        return product;
    }
}
