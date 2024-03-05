package org.ecommerce.pagamentos.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.ecommerce.pagamentos.service.TransacaoService;
import org.ecommerce.pedidos.dtos.PedidoDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
@Component
public class QueueConsumer {


    private final TransacaoService transacaoService;

    public QueueConsumer(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }
    @RabbitListener(queues = "${queue.name.pagamentos}")
    public void receive(@Payload String in) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            PedidoDTO pedidoDTO = mapper.readValue(in, PedidoDTO.class);
            this.transacaoService.salvarTransacao(pedidoDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
