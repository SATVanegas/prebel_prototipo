package com.prebel.prototipo.webapp.controllers.laboratory_reports_controllers;

import com.prebel.prototipo.webapp.services.login_services.HtmlEmailService;
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
    public ResponseEntity<String> sendHtmlEmail(
            @RequestParam String to,
            @RequestParam String subject,
            @RequestBody String htmlContent) { // Ahora el HTML se pasa en el body

        htmlEmailService.sendHtmlEmail(to, subject, htmlContent);
        return ResponseEntity.ok("Correo HTML enviado a " + to);
    }
}
