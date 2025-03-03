package com.prebel.prototipo.webapp.services.utils;

import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;
import com.prebel.prototipo.webapp.models.laboratory_reports.EnumTest;
import com.prebel.prototipo.webapp.models.laboratory_reports.Product;
import com.prebel.prototipo.webapp.models.laboratory_reports.tests.Condition;
import com.prebel.prototipo.webapp.models.laboratory_reports.tests.Storage;
import com.prebel.prototipo.webapp.models.laboratory_reports.tests.Temperature;
import com.prebel.prototipo.webapp.models.laboratory_reports.tests.Test;
import com.prebel.prototipo.webapp.models.role_module.User;
import com.prebel.prototipo.webapp.services.laboratory_reports_services.ProductService;
import org.springframework.stereotype.Service;
import java.util.function.Function;
import java.io.ByteArrayOutputStream;
import java.util.*;

@Service
public class PdfReportService {

    public byte[] createProductReport(Product product) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            PdfDocument pdfDocument = new PdfDocument(new PdfWriter(outputStream));
            Document documento = new Document(pdfDocument);
            documento.setFontSize(7);

            agregarEncabezadoDelReporte(documento);
            agregarTablaDeInformacionDelProducto(documento, product);
            agregarTablasDePruebas(documento, product);

            documento.close();
            return outputStream.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Error al generar el PDF", e);
        }
    }

    private void agregarEncabezadoDelReporte(Document documento) {
        documento.add(new Paragraph("REPORTE DE PRODUCTO").setBold().setFontSize(10).setTextAlignment(TextAlignment.CENTER));
    }
    // ========================================
    // TABLA: INFORMACIÓN DEL PRODUCTO
    // ========================================
    private void agregarTablaDeInformacionDelProducto(Document documento, Product product) {
        Table productTable = new Table(UnitValue.createPercentArray(6)).useAllAvailableWidth().setMargin(0).setPadding(0);

        agregarFilaCombinada(productTable, "Product description:", product.getProductDescription(), 2);
        agregarFilaDeInformacionDelProducto(productTable, "Formula number:", product.getFormulaNumber());
        agregarFilaDeInformacionDelProducto(productTable, "Qualification:", product.getQualification());
        agregarFilaDeInformacionDelProducto(productTable, "Project code:", product.getProjectCode());
        agregarFilaDeInformacionDelProducto(productTable, "Established validity:", product.getEstablishedValidity());
        agregarFilaDeInformacionDelProducto(productTable, "Reference:", product.getReference());
        agregarFilaDeInformacionDelProducto(productTable, "Project name:", product.getProjectName());
        agregarFilaDeInformacionDelProducto(productTable, "Responsible Chemist:", product.getResponsibleChemist(), User::getName);
        agregarFilaDeInformacionDelProducto(productTable, "Batch:", product.getBatch());
        agregarFilaDeInformacionDelProducto(productTable, "Customer:", product.getCustomer(), User::getName);
        agregarFilaDeInformacionDelProducto(productTable, "Responsible Engineer:", product.getResponsibleEngineer(), User::getName);
        agregarFilaDeInformacionDelProducto(productTable, "Packaging Type:", product.getPackagingType());
        agregarFilaDeInformacionDelProducto(productTable, "Brand:", product.getBrand());
        agregarFilaDeInformacionDelProducto(productTable, "Responsible Analyst:", product.getResponsibleAnalyst(), User::getName);
        agregarFilaDeInformacionDelProducto(productTable, "Packaging Material:", product.getPackagingMaterial());
        agregarFilaDeInformacionDelProducto(productTable, "Customer Contact:", product.getCustomer(), User::getEmail);
        agregarFilaDeInformacionDelProducto(productTable, "Technician in charge:", product.getTechnicianInCharge(), User::getName);
        agregarFilaDeInformacionDelProducto(productTable, "Container Color:", product.getContainerColor());
        agregarFilaDeInformacionDelProducto(productTable, "Study type:", product.getStudyType());
        agregarFilaDeInformacionDelProducto(productTable, "Study duration:", product.getStudyDuration(), d -> d + " months");
        agregarFilaDeInformacionDelProducto(productTable, "Lid Material:", product.getLidMaterial());
        agregarFilaDeInformacionDelProducto(productTable, "Consecutive:", product.getConsecutive(), String::valueOf);
        agregarFilaDeInformacionDelProducto(productTable, "Start Date:", product.getStartDate(), Object::toString);
        agregarFilaDeInformacionDelProducto(productTable, "Lid Color:", product.getLidColor());
        agregarFilaDeInformacionDelProducto(productTable, "Justification:", product.getJustification());
        agregarFilaDeInformacionDelProducto(productTable, "Finish Date:", product.getFinishDate(), Object::toString);

        documento.add(productTable);
    }

    private void agregarFilaCombinada(Table tabla, String titulo, String valor, int rowspan) {
        tabla.addCell(new Cell(rowspan, 1) // Combinación de filas
                .add(new Paragraph(titulo).setBold())
                .setBackgroundColor(new DeviceRgb(220, 220, 220)));

        tabla.addCell(new Cell(rowspan, 1)
                .add(new Paragraph(Optional.ofNullable(valor).orElse("No registrado"))));
    }

    private <T> void agregarFilaDeInformacionDelProducto(Table tabla, String titulo, T valor, Function<T, String> mapeador) {
        String valorConvertido = valor != null ? mapeador.apply(valor) : "No registrado";
        agregarFilaDeInformacionDelProducto(tabla, titulo, valorConvertido);
    }

    private void agregarFilaDeInformacionDelProducto(Table tabla, String titulo, String valor) {
        tabla.addCell(crearCeldaDeEncabezado(titulo));
        tabla.addCell(crearCeldaDeValor(valor));
    }

    private void agregarTablasDePruebas(Document documento, Product product) {
        if (product.getTests() != null && !product.getTests().isEmpty()) {
            for (Test test : product.getTests()) {
                Table tablaPrueba = crearTablaDePrueba(test);
                documento.add(tablaPrueba);

                agregarTablaDeAlmacenamiento(documento, test.getStorage());
                agregarTablaDeTemperatura(documento, test.getTemperature());
            }
        } else {
            agregarTablasDePruebasVacias(documento);
        }
    }
    // =======================
    // TABLA: TEST
    // =======================
    private Table crearTablaDePrueba(Test prueba) {
        Table tablaPrueba = new Table(UnitValue.createPercentArray(new float[]{12, 8, 23, 11, 23, 23})).useAllAvailableWidth().setMargin(0).setPadding(0);agregarEncabezadoDePrueba(tablaPrueba);

        List<Condition> conditions = (prueba.getConditions() != null) ? prueba.getConditions() : new ArrayList<>();
        EnumTest[] tiposDePrueba = EnumTest.values();

        for (EnumTest tipoDePrueba : tiposDePrueba) {
            Condition condition = conditions.stream()
                    .filter(c -> c.getType() == tipoDePrueba)
                    .findFirst()
                    .orElse(null);

            tablaPrueba.addCell(crearCeldaDeEncabezadoAlineada(tipoDePrueba.name()));
            tablaPrueba.addCell(crearCeldaDeEncabezadoAlineada(condition != null ? condition.getUnit() : "N/A"));
            tablaPrueba.addCell(crearCeldaDeValor(condition != null ? condition.getSpecification() : "No registrado"));
            tablaPrueba.addCell(crearCeldaDeValor(condition != null ? condition.getMethod() : "No registrado"));
            tablaPrueba.addCell(crearCeldaDeValor(condition != null ? condition.getInitialResultsDevelopmentLaboratory() : "No registrado"));
            tablaPrueba.addCell(crearCeldaDeValor(condition != null ? condition.getInitialResultsStabilityLaboratory() : "No registrado"));
        }

        return tablaPrueba;
    }
    // =======================
    // TABLA: STORAGE
    // =======================
    private void agregarTablaDeAlmacenamiento(Document documento, Storage storage) {
        Table tablaAlmacenamiento = new Table(UnitValue.createPercentArray(1)).useAllAvailableWidth().setMargin(0).setPadding(0);

        String condicionDeAlmacenamiento = (storage != null) ?
                storage.getMaxTemperature() + "°C ± " + (storage.getMaxTemperature() - storage.getMinTemperature()) + "°C"
                : "N/A";
        tablaAlmacenamiento.addCell(crearCeldaDeEncabezadoAlineada("STORAGE CONDITION: " + condicionDeAlmacenamiento));
        documento.add(tablaAlmacenamiento);

        Table tablaDetallesDeAlmacenamiento = new Table(UnitValue.createPercentArray(4)).useAllAvailableWidth();
        tablaDetallesDeAlmacenamiento.addCell(crearCeldaDeEncabezado("STORAGE EQUIPMENT CODE"));
        tablaDetallesDeAlmacenamiento.addCell(crearCeldaDeValor(storage != null ? storage.getEquipmentCode() : "No registrado"));
        tablaDetallesDeAlmacenamiento.addCell(crearCeldaDeEncabezado("DESCRIPTION"));
        tablaDetallesDeAlmacenamiento.addCell(crearCeldaDeValor(storage != null ? storage.getDescription() : "No registrado"));

        documento.add(tablaDetallesDeAlmacenamiento);
    }
    // =======================
    // TABLA: TEMPERATURE
    // =======================
    private void agregarTablaDeTemperatura(Document documento, Temperature temperature) {
        Table tablaTemperatura = new Table(UnitValue.createPercentArray(1)).useAllAvailableWidth().setMargin(0).setPadding(0);

        tablaTemperatura.addCell(crearCeldaDeEncabezadoAlineada("TIME (WEEK): " + (temperature != null ? temperature.getTime() : "")));
        documento.add(tablaTemperatura);

        Table tablaDetallesDeTemperatura = new Table(UnitValue.createPercentArray(4)).useAllAvailableWidth();
        tablaDetallesDeTemperatura.addCell(crearCeldaDeEncabezado("TEMPERATURE / UNIT"));
        tablaDetallesDeTemperatura.addCell(crearCeldaDeValor(temperature != null ? temperature.getUnit() : "No registrado"));
        tablaDetallesDeTemperatura.addCell(crearCeldaDeEncabezado("EQUIPMENT"));
        tablaDetallesDeTemperatura.addCell(crearCeldaDeValor(temperature != null ? String.valueOf(temperature.getEquipment()) : "No registrado"));

        documento.add(tablaDetallesDeTemperatura);
    }
    // =======================
    // TABLA: TESTS (VACÍA)
    // =======================
    private void agregarTablasDePruebasVacias(Document documento) {

        Table tablaPrueba = new Table(UnitValue.createPercentArray(6)).useAllAvailableWidth().setMargin(0).setPadding(0);
        agregarEncabezadoDePrueba(tablaPrueba);

        EnumTest[] tiposDePrueba = EnumTest.values();
        for (EnumTest tipoDePrueba : tiposDePrueba) {
            tablaPrueba.addCell(crearCeldaDeEncabezadoAlineada(tipoDePrueba.name()));
            tablaPrueba.addCell(crearCeldaDeEncabezadoAlineada("N/A"));
            tablaPrueba.addCell(crearCeldaDeValor("No registrado"));
            tablaPrueba.addCell(crearCeldaDeValor("No registrado"));
            tablaPrueba.addCell(crearCeldaDeValor("No registrado"));
            tablaPrueba.addCell(crearCeldaDeValor("No registrado"));
        }
        documento.add(tablaPrueba);

        agregarTablaDeAlmacenamiento(documento, null);
        agregarTablaDeTemperatura(documento, null);
    }

    private void agregarEncabezadoDePrueba(Table tabla) {
        List<String> encabezados = Arrays.asList("TEST", "UNIT", "SPECIFICATION", "METHOD", "INITIAL RESULTS DEVELOPMENT LABORATORY", "INITIAL RESULTS FROM STABILITY LABORATORY");for (String encabezado : encabezados) {
            tabla.addCell(crearCeldaDeEncabezadoAlineada(encabezado));
        }
    }

    private Cell crearCeldaDeEncabezado(String texto) {
        return new Cell().add(new Paragraph(texto)).setBold().setBackgroundColor(new DeviceRgb(220, 220, 220));
    }

    private Cell crearCeldaDeEncabezadoAlineada(String texto) {
        return new Cell().add(new Paragraph(texto)).setBold().setFontSize(7).setBackgroundColor(new DeviceRgb(220, 220, 220)).setTextAlignment(TextAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE).setPadding(5);
    }

    private Cell crearCeldaDeValor(String texto) {
        return new Cell().add(new Paragraph(texto != null && !texto.isEmpty() ? texto : "No registrado"));
    }

}