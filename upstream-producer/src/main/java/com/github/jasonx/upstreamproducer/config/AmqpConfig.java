package com.github.jasonx.upstreamproducer.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmqpConfig {
    @Bean
    public MessageConverter messageConverter(ObjectMapper objectMapper) {
        objectMapper.findAndRegisterModules(); // JavaTimeModule, etc.
        return new Jackson2JsonMessageConverter(objectMapper);
    }
}