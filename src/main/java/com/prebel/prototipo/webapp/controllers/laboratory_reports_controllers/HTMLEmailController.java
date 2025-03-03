package com.prebel.prototipo.webapp.controllers.laboratory_reports_controllers;

import com.prebel.prototipo.webapp.services.login_services.HtmlEmailService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
public class HTMLEmailController {

    private final HtmlEmailService htmlEmailService;

    public HTMLEmailController(HtmlEmailService htmlEmailService) {
        this.htmlEmailService = htmlEmailService;
    }

    @PostMapping("/send-html")
    public ResponseEntity<String> sendHtmlEmail(@RequestBody EmailRequest emailRequest) {
        htmlEmailService.sendHtmlEmail(emailRequest.getTo(), emailRequest.getSubject(), emailRequest.getHtmlContent());
        return ResponseEntity.ok("Correo HTML enviado a " + emailRequest.getTo());
    }

    @Getter
    @Setter
    public static class EmailRequest {
        private String to;
        private String subject;
        private String htmlContent;

        // Getters y setters
    }
}
