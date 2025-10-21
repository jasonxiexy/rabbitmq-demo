package com.github.jasonx.upstreamproducer.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    // Exchange names
    public static final String DIRECT_EXCHANGE = "order.command.direct";
    public static final String TOPIC_EXCHANGE  = "order.events.topic";
    public static final String FANOUT_EXCHANGE = "system.alerts.fanout";

    // Routing keys (example)
    public static final String ORDER_CREATE_RK = "order.create";
    public static final String ORDER_EVENT_CREATED = "order.created";
    public static final String ORDER_EVENT_PAID    = "order.paid";
    public static final String ORDER_EVENT_SHIPPED = "order.shipped";

    @Bean
    public DirectExchange orderCommandDirectExchange() {
        return ExchangeBuilder.directExchange(DIRECT_EXCHANGE).durable(true).build();
    }

    @Bean
    public TopicExchange orderEventsTopicExchange() {
        return ExchangeBuilder.topicExchange(TOPIC_EXCHANGE).durable(true).build();
    }

    @Bean
    public FanoutExchange systemAlertsFanoutExchange() {
        return ExchangeBuilder.fanoutExchange(FANOUT_EXCHANGE).durable(true).build();
    }
}