package com.github.jasonx.downstreamconsumer.listener;

import com.github.jasonx.downstreamconsumer.config.RabbitConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DirectOrderListener {

    @RabbitListener(queues = RabbitConfig.Q_ORDER_COMMANDS)
    public void onOrderCommand(@Payload String payload) {
        log.info("[DIRECT] Received OrderCommand -> {}", payload);
        // parse & handle command (create DB record, call another service, etc.)
    }
}