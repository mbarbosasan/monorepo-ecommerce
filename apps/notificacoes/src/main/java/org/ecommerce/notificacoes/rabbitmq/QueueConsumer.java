package org.ecommerce.notificacoes.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.ecommerce.notificacoes.enums.TipoNotificacao;
import org.ecommerce.notificacoes.service.NotificacoesService;
import org.ecommerce.pedidos.dtos.PedidoDTO;
import org.ecommerce.pedidos.enums.StatusPedido;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class QueueConsumer {

    private final NotificacoesService notificacoesService;

    public QueueConsumer(NotificacoesService notificacoesService) {
        this.notificacoesService = notificacoesService;
    }


    @RabbitListener(queues = "${queue.name.notificacoes}")
    public void receive(String in) {
        System.out.println(" [x] Received '" + in + "'");

        try {
            ObjectMapper mapper = new ObjectMapper();
            PedidoDTO pedidoDTO = mapper.readValue(in, PedidoDTO.class);
            if (pedidoDTO.getStatus() == StatusPedido.AGUARDANDO_PAGAMENTO) {
                this.notificacoesService.sendEmail("moisesbarbosa23@gmail.com", TipoNotificacao.PEDIDO_RECEBIDO);
            } else {
                this.notificacoesService.sendEmail("moisesbarbosa23@gmail.com", TipoNotificacao.PAGAMENTO_APROVADO);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
