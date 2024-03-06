package org.ecommerce.notificacoes.service;

import org.ecommerce.notificacoes.enums.TipoNotificacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class NotificacoesService {

    private final JavaMailSender javaMailSender;

    public NotificacoesService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Value("{spring.mail.username}")
    private String fromMail;

    public void sendEmail(String to, TipoNotificacao TIPO_NOTIFICACAO) {
        if (Objects.equals(TIPO_NOTIFICACAO, TipoNotificacao.PEDIDO_RECEBIDO)) {
            javaMailSender.send(criarPedidoEfetuado(to));
        } else if (Objects.equals(TIPO_NOTIFICACAO, TipoNotificacao.PAGAMENTO_APROVADO)) {
            javaMailSender.send(criarPedidoPagamentoConfirmado(to));
        }
        System.out.println("Email enviado paizão, agora é rezar escarpinha.");
    }

    public SimpleMailMessage criarPedidoEfetuado(String to) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromMail);
        message.setTo(to);
        message.setSubject("Pedido efetuado com sucesso!");
        message.setText("Seu pedido foi recebido por nossa equipe e estamos aguardando o pagamento. Em breve você receberá um novo email com as informações de entrega caso o pagamento seja aprovado. Obrigado por comprar conosco!");

        return message;
    }

    public SimpleMailMessage criarPedidoPagamentoConfirmado(String to) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromMail);
        message.setTo(to);
        message.setSubject("Pagamento confirmado!");
        message.setText("Seu pagamento foi confirmado e seu pedido está em processo de entrega. Em breve você receberá um novo email com as informações de entrega. Obrigado por comprar conosco!");

        return message;
    }
}
