package org.ecommerce.notificacoes;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRabbit
@SpringBootApplication
public class NotificacoesApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotificacoesApplication.class, args);
    }

}
