package com.prebel.prototipo.webapp.controllers.laboratory_reports_controllers;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class EmailServicePdf {

    private final JavaMailSender mailSender;

    public EmailServicePdf(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void enviarCorreoConAdjunto(String destinatario, String asunto, byte[] archivoAdjunto)
            throws MessagingException, IOException {

        MimeMessage mensaje = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");

        helper.setTo(destinatario);
        helper.setSubject(asunto);
        helper.setFrom("tu_correo@prebel.com");

        // Cargar la plantilla HTML desde resources/templates/
        String htmlContent = cargarPlantillaHtml();
        helper.setText(htmlContent, true);

        // Adjuntar el PDF
        helper.addAttachment("product_report.pdf", new ByteArrayDataSource(archivoAdjunto, "application/pdf"));

        mailSender.send(mensaje);
    }

    private String cargarPlantillaHtml() throws IOException {
        Resource resource = new ClassPathResource("templates/product_report_email.html");
        return new String(Files.readAllBytes(Paths.get(resource.getURI())));
    }
}
