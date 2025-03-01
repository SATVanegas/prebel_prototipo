package com.prebel.prototipo.webapp.services.utils;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import com.prebel.prototipo.webapp.models.laboratory_reports.Product;
import com.prebel.prototipo.webapp.models.laboratory_reports.tests.Test;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@Service
public class PdfReportService {

    public byte[] createProductReport(Product product) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(outputStream);
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument);

            // Título
            document.add(new Paragraph("Reporte de Producto").setBold().setFontSize(18));

            // Información del producto
            Table productTable = new Table(2);
            productTable.addCell(new Cell().add(new Paragraph("Descripción:")).setBold());
            productTable.addCell(new Cell().add(new Paragraph(safeText(product.getProductDescription()))));
            productTable.addCell(new Cell().add(new Paragraph("Referencia:")).setBold());
            productTable.addCell(new Cell().add(new Paragraph(safeText(product.getReference()))));
            productTable.addCell(new Cell().add(new Paragraph("Lote:")).setBold());
            productTable.addCell(new Cell().add(new Paragraph(safeText(product.getBatch()))));
            document.add(productTable);

            // Tabla de Tests
            document.add(new Paragraph("\nTests Realizados:").setBold().setFontSize(14));
            Table testTable = new Table(4);
            testTable.addCell(new Cell().add(new Paragraph("ID")));
            testTable.addCell(new Cell().add(new Paragraph("Observaciones")));
            testTable.addCell(new Cell().add(new Paragraph("Conclusión")));
            testTable.addCell(new Cell().add(new Paragraph("Temperatura")));

            for (Test test : Optional.ofNullable(product.getTests()).orElse(Collections.emptyList())) {
                testTable.addCell(new Cell().add(new Paragraph(String.valueOf(test.getId()))));
                testTable.addCell(new Cell().add(new Paragraph(safeText(test.getObservations()))));
                testTable.addCell(new Cell().add(new Paragraph(safeText(test.getConclusion()))));
                testTable.addCell(new Cell().add(new Paragraph(safeText(test.getTemperature().getUnit()))));
            }

            document.add(testTable);
            document.close();

            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Error al generar el PDF", e);
        }
    }

    // Método auxiliar para evitar valores nulos
    private String safeText(String text) {
        return text != null ? text : "";
    }

}