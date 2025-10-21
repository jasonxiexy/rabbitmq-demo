package com.github.jasonx.downstreamconsumer.listener;

import com.github.jasonx.downstreamconsumer.config.RabbitConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TopicOrderListener {

    @RabbitListener(queues = RabbitConfig.Q_ORDER_EVENTS_ALL)
    public void onOrderEvent(@Payload String payload) {
        log.info("[TOPIC] Received OrderEvent -> {}", payload);
        // branch by event type if needed
    }
}