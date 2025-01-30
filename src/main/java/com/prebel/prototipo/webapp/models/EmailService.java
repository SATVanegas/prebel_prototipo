package com.prebel.prototipo.webapp.models;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    // Método para enviar un correo electrónico
    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("prebel.notifications@gmail.com"); // Dirección de correo desde la cual se enviará el mensaje
        message.setTo(to); // Dirección de correo del destinatario
        message.setSubject(subject); // Asunto del correo
        message.setText(text); // Cuerpo del correo

        mailSender.send(message);
    }

}

