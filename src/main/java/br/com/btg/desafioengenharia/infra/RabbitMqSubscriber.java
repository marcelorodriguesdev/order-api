package br.com.btg.desafioengenharia.infra;

import br.com.btg.desafioengenharia.request.PedidoRequest;
import br.com.btg.desafioengenharia.service.RabbitMqService;
import br.com.btg.desafioengenharia.utils.LoggerResponse;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import static br.com.btg.desafioengenharia.utils.LoggerUtils.INFO;
import static br.com.btg.desafioengenharia.utils.LoggerUtils.log;

@Component
public class RabbitMqSubscriber {

    @Autowired
    private RabbitMqService rabbitMqService;

    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void receberPedido(@Payload PedidoRequest payload) {
        log(INFO, new LoggerResponse("Novo pedido recebido", payload));
        rabbitMqService.tratarPedido(payload);
    }

}