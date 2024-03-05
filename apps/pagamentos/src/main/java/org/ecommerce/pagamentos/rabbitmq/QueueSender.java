package org.ecommerce.pagamentos.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QueueSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(String message, String queueName) {
        rabbitTemplate.convertAndSend(queueName, message);
    }
}
