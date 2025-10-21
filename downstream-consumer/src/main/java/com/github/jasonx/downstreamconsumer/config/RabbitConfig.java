package com.github.jasonx.downstreamconsumer.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    // Exchange names (must match producer)
    public static final String DIRECT_EXCHANGE = "order.command.direct";
    public static final String TOPIC_EXCHANGE  = "order.events.topic";
    public static final String FANOUT_EXCHANGE = "system.alerts.fanout";

    // Routing keys
    public static final String ORDER_CREATE_RK = "order.create";

    // Queue names
    public static final String Q_ORDER_COMMANDS   = "q.order.commands";
    public static final String Q_ORDER_EVENTS_ALL = "q.order.events.all";
    public static final String Q_ALERTS_AUDIT     = "q.alerts.audit";
    public static final String Q_ALERTS_DASHBOARD = "q.alerts.dashboard";

    // Exchanges
    @Bean public DirectExchange orderCommandDirectExchange() {
        return ExchangeBuilder.directExchange(DIRECT_EXCHANGE).durable(true).build();
    }
    @Bean public TopicExchange orderEventsTopicExchange() {
        return ExchangeBuilder.topicExchange(TOPIC_EXCHANGE).durable(true).build();
    }
    @Bean public FanoutExchange systemAlertsFanoutExchange() {
        return ExchangeBuilder.fanoutExchange(FANOUT_EXCHANGE).durable(true).build();
    }

    // Queues
    @Bean public Queue qOrderCommands() {
        return QueueBuilder.durable(Q_ORDER_COMMANDS).build();
    }
    @Bean public Queue qOrderEventsAll() {
        return QueueBuilder.durable(Q_ORDER_EVENTS_ALL).build();
    }
    @Bean public Queue qAlertsAudit() {
        return QueueBuilder.durable(Q_ALERTS_AUDIT).build();
    }
    @Bean public Queue qAlertsDashboard() {
        return QueueBuilder.durable(Q_ALERTS_DASHBOARD).build();
    }

    // Bindings (Direct)
    @Bean
    public Binding bindDirectCreate(Queue qOrderCommands, DirectExchange orderCommandDirectExchange) {
        return BindingBuilder.bind(qOrderCommands)
                .to(orderCommandDirectExchange)
                .with(ORDER_CREATE_RK);
    }

    // Bindings (Topic) — subscribe to all order.* events
    @Bean
    public Binding bindTopicAll(Queue qOrderEventsAll, TopicExchange orderEventsTopicExchange) {
        return BindingBuilder.bind(qOrderEventsAll)
                .to(orderEventsTopicExchange)
                .with("order.*");
    }

    // Bindings (Fanout) — both queues receive all alerts
    @Bean
    public Binding bindFanoutAudit(Queue qAlertsAudit, FanoutExchange systemAlertsFanoutExchange) {
        return BindingBuilder.bind(qAlertsAudit).to(systemAlertsFanoutExchange);
    }
    @Bean
    public Binding bindFanoutDashboard(Queue qAlertsDashboard, FanoutExchange systemAlertsFanoutExchange) {
        return BindingBuilder.bind(qAlertsDashboard).to(systemAlertsFanoutExchange);
    }
}