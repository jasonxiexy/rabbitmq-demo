package com.github.jasonx.downstreamconsumer.listener;

import com.github.jasonx.downstreamconsumer.config.RabbitConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FanoutAlertListener {

    @RabbitListener(queues = RabbitConfig.Q_ALERTS_AUDIT)
    public void onAudit(@Payload String payload) {
        // could write to an audit log sink
        log.info("[FANOUT/AUDIT] Received SystemAlert -> {}", payload);
    }

    @RabbitListener(queues = RabbitConfig.Q_ALERTS_DASHBOARD)
    public void onDashboard(@Payload String payload) {
        // could push to WebSocket/SSE for dashboards
        log.info("[FANOUT/DASHBOARD] Received SystemAlert -> {}", payload);
    }
}