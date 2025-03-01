package com.prebel.prototipo.webapp.services.utils;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.properties.UnitValue;
import com.prebel.prototipo.webapp.models.laboratory_reports.EnumTest;
import com.prebel.prototipo.webapp.models.laboratory_reports.Product;
import com.prebel.prototipo.webapp.models.laboratory_reports.tests.Condition;
import com.prebel.prototipo.webapp.models.laboratory_reports.tests.Storage;
import com.prebel.prototipo.webapp.models.laboratory_reports.tests.Temperature;
import com.prebel.prototipo.webapp.models.laboratory_reports.tests.Test;
import com.prebel.prototipo.webapp.models.role_module.User;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;
import java.util.*;

@Service
public class PdfReportService {

    public byte[] createProductReport(Product product) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            PdfDocument pdfDocument = new PdfDocument(new PdfWriter(outputStream));
            Document document = new Document(pdfDocument);

            // =======================
            // ENCABEZADO DEL REPORTE
            // =======================
            document.add(new Paragraph("Reporte de Producto").setBold().setFontSize(14));

            // ========================================
            // TABLA: INFORMACIÓN DEL PRODUCTO
            // ========================================
            Table productTable = new Table(UnitValue.createPercentArray(6)).useAllAvailableWidth();

            // --------------------------
            // FILA 1
            // --------------------------
            Cell productDescCell = new Cell(2, 1)
                    .add(new Paragraph("Product description:").setBold())
                    .setBackgroundColor(ColorConstants.LIGHT_GRAY);
            productTable.addCell(productDescCell);

            Cell productDescValueCell = new Cell(2, 1)
                    .add(new Paragraph(Optional.ofNullable(product.getProductDescription()).orElse("No registrado")));
            productTable.addCell(productDescValueCell);

            productTable.addCell(createHeaderCell("Formula number:"));
            productTable.addCell(createValueCell(Optional.ofNullable(product.getFormulaNumber()).orElse("No registrado")));
            productTable.addCell(createHeaderCell("Qualification:"));
            productTable.addCell(createValueCell(Optional.ofNullable(product.getQualification()).orElse("No registrado")));
            // --------------------------
            // FILA 2
            // --------------------------
            productTable.addCell(createHeaderCell("Project code:"));
            productTable.addCell(createValueCell(Optional.ofNullable(product.getProjectCode()).orElse("No registrado")));
            productTable.addCell(createHeaderCell("Established validity:"));
            productTable.addCell(createValueCell(Optional.ofNullable(product.getEstablishedValidity()).orElse("No registrado")));
            // --------------------------
            // FILA 3
            // --------------------------
            addProductInfo(productTable, "Reference:", Optional.ofNullable(product.getReference()).orElse("No registrado"));
            addProductInfo(productTable, "Project name:", Optional.ofNullable(product.getProjectName()).orElse("No registrado"));
            addProductInfo(productTable, "Responsible Chemist:", Optional.ofNullable(product.getResponsibleChemist()).map(User::getName).orElse("No registrado"));
            // --------------------------
            // FILA 4
            // --------------------------
            addProductInfo(productTable, "Batch:", Optional.ofNullable(product.getBatch()).orElse("No registrado"));
            addProductInfo(productTable, "Customer:", Optional.ofNullable(product.getCustomer()).map(User::getName).orElse("No registrado"));
            addProductInfo(productTable, "Responsible Engineer:", Optional.ofNullable(product.getResponsibleEngineer()).map(User::getName).orElse("No registrado"));
            // --------------------------
            // FILA 5
            // --------------------------
            addProductInfo(productTable, "Packaging Type:", Optional.ofNullable(product.getPackagingType()).orElse("No registrado"));
            addProductInfo(productTable, "Brand:", Optional.ofNullable(product.getBrand()).orElse("No registrado"));
            addProductInfo(productTable, "Responsible Analyst:", Optional.ofNullable(product.getResponsibleAnalyst()).map(User::getName).orElse("No registrado"));
            // --------------------------
            // FILA 6
            // --------------------------
            addProductInfo(productTable, "Packaging Material:", Optional.ofNullable(product.getPackagingMaterial()).orElse("No registrado"));
            addProductInfo(productTable, "Customer Contact:", Optional.ofNullable(product.getCustomer()).map(User::getEmail).orElse("No registrado"));
            addProductInfo(productTable, "Technician in charge:", Optional.ofNullable(product.getTechnicianInCharge()).map(User::getName).orElse("No registrado"));
            // --------------------------
            // FILA 7
            // --------------------------
            addProductInfo(productTable, "Container Color:", Optional.ofNullable(product.getContainerColor()).orElse("No registrado"));
            addProductInfo(productTable, "Study type:", Optional.ofNullable(product.getStudyType()).orElse("No registrado"));
            addProductInfo(productTable, "Study duration:", Optional.of(product.getStudyDuration()).map(d -> d + " months").orElse("No registrado"));
            // --------------------------
            // FILA 8
            // --------------------------
            addProductInfo(productTable, "Lid Material:", Optional.ofNullable(product.getLidMaterial()).orElse("No registrado"));
            addProductInfo(productTable, "Consecutive:", Optional.of(product.getConsecutive()).map(String::valueOf).orElse("No registrado"));
            addProductInfo(productTable, "Start Date:", Optional.ofNullable(product.getStartDate()).map(Object::toString).orElse("No registrado"));
            // --------------------------
            // FILA 9
            // --------------------------
            addProductInfo(productTable, "Lid Color:", Optional.ofNullable(product.getLidColor()).orElse("No registrado"));
            addProductInfo(productTable, "Justification:", Optional.ofNullable(product.getJustification()).orElse("No registrado"));
            addProductInfo(productTable, "Finish Date:", Optional.ofNullable(product.getFinishDate()).map(Object::toString).orElse("No registrado"));

            document.add(productTable);

            // =======================
            // TABLA: TESTS
            // =======================
            document.add(new Paragraph("\n")); // Espacio antes de la tabla

            if (product.getTests() != null && !product.getTests().isEmpty()) {
                // Si hay al menos un test, iteramos sobre todos
                for (Test test : product.getTests()) {
                    document.add(new Paragraph("\n")); // Espacio antes de cada test

                    // =======================
                    // TABLA: TESTS
                    // =======================
                    Table testTable = new Table(UnitValue.createPercentArray(6)).useAllAvailableWidth();
                    addTestHeader(testTable);

                    List<Condition> conditions = (test.getConditions() != null) ? test.getConditions() : new ArrayList<>();
                    EnumTest[] tests = EnumTest.values();

                    // --------------------------
                    // FILAS DE CONDITIONS
                    // --------------------------
                    for (EnumTest testType : tests) {
                        Condition condition = conditions.stream()
                                .filter(c -> c.getType() == testType)
                                .findFirst()
                                .orElse(null);

                        testTable.addCell(createValueCell(testType.name()));
                        testTable.addCell(createValueCell(condition != null ? condition.getUnit() : "No registrado"));
                        testTable.addCell(createValueCell(condition != null ? condition.getSpecification() : "No registrado"));
                        testTable.addCell(createValueCell(condition != null ? condition.getMethod() : "No registrado"));
                        testTable.addCell(createValueCell(condition != null ? condition.getInitialResultsDevelopmentLaboratory() : "No registrado"));
                        testTable.addCell(createValueCell(condition != null ? condition.getInitialResultsStabilityLaboratory() : "No registrado"));
                    }

                    document.add(testTable);

                    // =======================
                    // TABLA: STORAGE
                    // =======================
                    Storage storage = test.getStorage();

                    // --------------------------
                    // FILA 1: CONDICIÓN DE ALMACENAMIENTO
                    // --------------------------
                    String storageCondition = (storage != null) ?
                            storage.getMaxTemperature() + "°C ± " + (storage.getMaxTemperature() - storage.getMinTemperature()) + "°C"
                            : "No registrado";

                    Table storageTable = new Table(UnitValue.createPercentArray(1)).useAllAvailableWidth();
                    storageTable.addCell(createHeaderCell("STORAGE CONDITION: " + storageCondition));
                    document.add(storageTable);

                    // --------------------------
                    // FILA 2: DETALLES DE STORAGE
                    // --------------------------
                    Table storageDetailsTable = new Table(UnitValue.createPercentArray(4)).useAllAvailableWidth();
                    storageDetailsTable.addCell(createHeaderCell("STORAGE EQUIPMENT CODE"));
                    storageDetailsTable.addCell(createValueCell(storage != null ? storage.getEquipmentCode() : "No registrado"));
                    storageDetailsTable.addCell(createHeaderCell("DESCRIPTION"));
                    storageDetailsTable.addCell(createValueCell(storage != null ? storage.getDescription() : "No registrado"));

                    document.add(storageDetailsTable);

                    // =======================
                    // TABLA: TEMPERATURE
                    // =======================
                    Temperature temperature = test.getTemperature();

                    // --------------------------
                    // FILA 1: ENCABEZADO TEMPERATURA
                    // --------------------------
                    Table temperatureTable = new Table(UnitValue.createPercentArray(1)).useAllAvailableWidth();
                    temperatureTable.addCell(createHeaderCell("TEMPERATURE / UNIT"));
                    document.add(temperatureTable);

                    // --------------------------
                    // FILA 2: DETALLES TEMPERATURA
                    // --------------------------
                    Table temperatureDetailsTable = new Table(UnitValue.createPercentArray(4)).useAllAvailableWidth();
                    temperatureDetailsTable.addCell(createHeaderCell("TEMPERATURE / UNIT"));
                    temperatureDetailsTable.addCell(createValueCell(temperature != null ? temperature.getUnit() : "No registrado"));
                    temperatureDetailsTable.addCell(createHeaderCell("EQUIPMENT"));
                    temperatureDetailsTable.addCell(createValueCell(temperature != null ? String.valueOf(temperature.getEquipment()) : "No registrado"));

                    document.add(temperatureDetailsTable);
                }
            } else {
                // =======================
                // CASO: NO HAY TESTS, SE CREAN TABLAS VACÍAS
                // =======================
                document.add(new Paragraph("\n")); // Espacio antes de la tabla

                // =======================
                // TABLA: TESTS (VACÍA)
                // =======================
                Table testTable = new Table(UnitValue.createPercentArray(6)).useAllAvailableWidth();
                addTestHeader(testTable);

                // Se agregan filas vacías con "No registrado"
                EnumTest[] tests = EnumTest.values();
                for (EnumTest testType : tests) {
                    testTable.addCell(createValueCell(testType.name()));
                    testTable.addCell(createValueCell("No registrado"));
                    testTable.addCell(createValueCell("No registrado"));
                    testTable.addCell(createValueCell("No registrado"));
                    testTable.addCell(createValueCell("No registrado"));
                    testTable.addCell(createValueCell("No registrado"));
                }
                document.add(testTable);

                // =======================
                // TABLA: STORAGE (VACÍA)
                // =======================
                Table storageTable = new Table(UnitValue.createPercentArray(1)).useAllAvailableWidth();
                storageTable.addCell(createHeaderCell("STORAGE CONDITION: No registrado"));
                document.add(storageTable);

                Table storageDetailsTable = new Table(UnitValue.createPercentArray(4)).useAllAvailableWidth();
                storageDetailsTable.addCell(createHeaderCell("STORAGE EQUIPMENT CODE"));
                storageDetailsTable.addCell(createValueCell("No registrado"));
                storageDetailsTable.addCell(createHeaderCell("DESCRIPTION"));
                storageDetailsTable.addCell(createValueCell("No registrado"));

                document.add(storageDetailsTable);

                // =======================
                // TABLA: TEMPERATURE (VACÍA)
                // =======================
                Table temperatureTable = new Table(UnitValue.createPercentArray(1)).useAllAvailableWidth();
                temperatureTable.addCell(createHeaderCell("TEMPERATURE / UNIT"));
                document.add(temperatureTable);

                Table temperatureDetailsTable = new Table(UnitValue.createPercentArray(4)).useAllAvailableWidth();
                temperatureDetailsTable.addCell(createHeaderCell("TEMPERATURE / UNIT"));
                temperatureDetailsTable.addCell(createValueCell("No registrado"));
                temperatureDetailsTable.addCell(createHeaderCell("EQUIPMENT"));
                temperatureDetailsTable.addCell(createValueCell("No registrado"));

                document.add(temperatureDetailsTable);
            }


            document.close();
            return outputStream.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Error al generar el PDF", e);
        }
    }

    private void addProductInfo(Table table, String title, String value) {
        table.addCell(createHeaderCell(title));
        table.addCell(createValueCell(value));
    }

    private void addTestHeader(Table table) {
        List<String> headers = Arrays.asList("TEST", "UNIT", "SPECIFICATION", "METHOD", "INITIAL RESULTS DEVELOPMENT LABORATORY", "INITIAL RESULTS FROM STABILITY LABORATORY");
        for (String header : headers) {
            table.addCell(createHeaderCell(header));
        }
    }

    private Cell createHeaderCell(String text) {
        return new Cell().add(new Paragraph(text)).setBold().setBackgroundColor(new DeviceRgb(220, 220, 220));
    }

    private Cell createValueCell(String text) {
        if (text == null) {
            text = "No registrado";
        }
        return new Cell().add(new Paragraph(text.isEmpty() ? "No registrado" : text));
    }


}