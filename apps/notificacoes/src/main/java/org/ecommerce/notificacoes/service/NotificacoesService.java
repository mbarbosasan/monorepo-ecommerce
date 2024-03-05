package org.ecommerce.notificacoes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificacoesService {

    private final JavaMailSender javaMailSender;

    public NotificacoesService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Value("{spring.mail.username}")
    private String fromMail;

    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromMail);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        javaMailSender.send(message);

        System.out.println("Email enviado paizão, agora é rezar");
    }
}
