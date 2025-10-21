package com.github.jasonx.upstreamproducer.controller;

import com.github.jasonx.upstreamproducer.config.RabbitConfig;
import com.github.jasonx.upstreamproducer.model.OrderCommand;
import com.github.jasonx.upstreamproducer.model.OrderEvent;
import com.github.jasonx.upstreamproducer.model.SystemAlert;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/send")
@RequiredArgsConstructor
public class SendController {

    private final RabbitTemplate template;

    // 1) DIRECT: send an OrderCommand with exact routing key
    @PostMapping("/command/create")
    public String sendCreateCommand(@RequestBody OrderCommand cmd) {
        template.convertAndSend(
                RabbitConfig.DIRECT_EXCHANGE,
                RabbitConfig.ORDER_CREATE_RK,
                cmd
        );
        return "Sent OrderCommand 'order.create' for orderId=" + cmd.getOrderId();
    }

    // 2) TOPIC: send OrderEvent (type decides routing key)
    @PostMapping("/event")
    public String sendOrderEvent(@RequestBody OrderEvent event) {
        String rk = switch (event.getType()) {
            case "created" -> RabbitConfig.ORDER_EVENT_CREATED;
            case "paid"    -> RabbitConfig.ORDER_EVENT_PAID;
            case "shipped" -> RabbitConfig.ORDER_EVENT_SHIPPED;
            default        -> "order.unknown";
        };
        template.convertAndSend(RabbitConfig.TOPIC_EXCHANGE, rk, event);
        return "Sent OrderEvent '" + rk + "' for orderId=" + event.getOrderId();
    }

    // 3) FANOUT: broadcast a SystemAlert
    @PostMapping("/alert")
    public String sendAlert(@RequestBody SystemAlert alert) {
        template.convertAndSend(
                RabbitConfig.FANOUT_EXCHANGE,
                "", // routing key ignored by fanout
                alert
        );
        return "Broadcasted alert: " + alert.getSeverity();
    }
}