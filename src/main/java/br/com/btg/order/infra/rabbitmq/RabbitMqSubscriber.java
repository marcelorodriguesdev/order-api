package br.com.btg.order.infra.rabbitmq;

import br.com.btg.order.api.request.OrderRequest;
import br.com.btg.order.core.service.RabbitMqService;
import br.com.btg.order.api.utils.LoggerResponse;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import static br.com.btg.order.api.utils.LoggerUtils.INFO;
import static br.com.btg.order.api.utils.LoggerUtils.log;

@Component
public class RabbitMqSubscriber {

    @Autowired
    private RabbitMqService rabbitMqService;

    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void receberPedido(@Payload OrderRequest payload) {
        log(INFO, new LoggerResponse("Novo pedido recebido", payload));
        rabbitMqService.processOrder(payload);
    }

}