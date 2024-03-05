package org.ecommerce.notificacoes.rabbitmq;

import org.ecommerce.notificacoes.service.NotificacoesService;
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
        this.notificacoesService.sendEmail("moisesbarbosa23@gmail.com", "Compra realizada com sucesso", "Sua compra foi realizada com sucesso, obrigado por comprar conosco!");
    }

}
