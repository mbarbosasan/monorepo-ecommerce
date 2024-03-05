package org.ecommerce.pagamentos.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.ecommerce.pedidos.dtos.CarrinhoDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
@Component
public class QueueConsumer {
    @RabbitListener(queues = "${queue.name.pagamentos}")
    public void receive(@Payload String in) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            CarrinhoDTO carrinho = mapper.readValue(in, CarrinhoDTO.class);
            System.out.println(carrinho);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
