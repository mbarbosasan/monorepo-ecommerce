package org.ecommerce.notificacoes.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.ecommerce.notificacoes.enums.TipoNotificacao;
import org.ecommerce.pedidos.dtos.PedidoDTO;
import org.ecommerce.usuarios.domain.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;

@Service
public class NotificacoesService {

    private final JavaMailSender javaMailSender;
    @Value("{spring.mail.username}")
    private String fromMail;

    public NotificacoesService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendEmail(PedidoDTO pedidoDTO, TipoNotificacao TIPO_NOTIFICACAO) {
        Usuario usuario = buscarUsuarioPorId(pedidoDTO.getIdCliente());
        if (Objects.equals(TIPO_NOTIFICACAO, TipoNotificacao.PEDIDO_RECEBIDO)) {
            javaMailSender.send(criarPedidoEfetuado(usuario));
        } else if (Objects.equals(TIPO_NOTIFICACAO, TipoNotificacao.PAGAMENTO_APROVADO)) {
            javaMailSender.send(criarPedidoPagamentoConfirmado(usuario));
        }
        System.out.println("Email enviado paizão, agora é rezar escarpinha.");
    }

    private SimpleMailMessage criarPedidoEfetuado(Usuario usuario) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromMail);
        message.setTo(usuario.getEmail());
        message.setSubject("Pedido efetuado com sucesso!");
        message.setText("Olá, " + usuario.getNome() + "! Seu pedido foi recebido por nossa equipe e estamos aguardando o pagamento. Em breve você receberá um novo email com as informações de entrega caso o pagamento seja aprovado. Obrigado por comprar conosco!");

        return message;
    }

    private SimpleMailMessage criarPedidoPagamentoConfirmado(Usuario usuario) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromMail);
        message.setTo(usuario.getEmail());
        message.setSubject("Pagamento confirmado!");
        message.setText("Olá, " + usuario.getNome() + "! Seu pagamento foi confirmado e seu pedido está em processo de entrega. Em breve você receberá um novo email com as informações de entrega. Obrigado por comprar conosco!");

        return message;
    }

    private Usuario buscarUsuarioPorId(Long id) {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8084/usuarios/" + id))
                .GET()
                .build();

        try {
            HttpResponse<String> response = HttpClient.newHttpClient().send(httpRequest, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new RuntimeException("Usuário não encontrado");
            }
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(response.body(), Usuario.class);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
