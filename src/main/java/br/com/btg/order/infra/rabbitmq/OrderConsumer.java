package br.com.btg.order.infra.rabbitmq;

import br.com.btg.order.api.request.OrderRequest;
import br.com.btg.order.core.service.RabbitMQService;
import br.com.btg.order.api.utils.LoggerResponse;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import static br.com.btg.order.api.utils.LoggerUtils.INFO;
import static br.com.btg.order.api.utils.LoggerUtils.log;

@Component
public class OrderConsumer {

    @Autowired
    private RabbitMQService rabbitMqService;

    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void orderListener(@Payload OrderRequest payload) {
        log(INFO, new LoggerResponse("New order received", payload));
        rabbitMqService.processOrder(payload);
    }
}
