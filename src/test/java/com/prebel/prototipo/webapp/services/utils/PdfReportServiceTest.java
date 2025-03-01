package com.prebel.prototipo.webapp.services.utils;

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
import java.util.Arrays;

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

        // Verificar que se generó contenido
        assertNotNull(pdfBytes);
        assertTrue(pdfBytes.length > 0);

        // Leer el contenido del PDF usando PDFBox
        try (PDDocument document = PDDocument.load(new ByteArrayInputStream(pdfBytes))) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            String pdfText = pdfStripper.getText(document);

            // Verificar que el contenido del PDF contiene información del producto
            assertTrue(pdfText.contains("Reporte de Producto"));
            assertTrue(pdfText.contains("Descripción:"));
            assertTrue(pdfText.contains(product.getProductDescription()));
            assertTrue(pdfText.contains("Referencia:"));
            assertTrue(pdfText.contains(product.getReference()));
            assertTrue(pdfText.contains("Lote:"));
            assertTrue(pdfText.contains(product.getBatch()));

            // Verificar que contiene información de los tests
            for (com.prebel.prototipo.webapp.models.laboratory_reports.tests.Test test : product.getTests()) {
                assertTrue(pdfText.contains(String.valueOf(test.getId())));
                assertTrue(pdfText.contains(test.getObservations()));
                assertTrue(pdfText.contains(test.getConclusion()));
                assertTrue(pdfText.contains(test.getTemperature().getUnit()));
            }
        }
    }

    private Product crearProductoDePrueba() {
        Product product = new Product();
        product.setProductDescription("Producto de prueba");
        product.setReference("REF123");
        product.setBatch("BATCH001");

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
