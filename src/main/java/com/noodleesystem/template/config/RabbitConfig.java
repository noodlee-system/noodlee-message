package com.noodleesystem.template.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    static final String queueName = "template_queue";

    @Bean
    public Queue testQueue() {
        return new Queue(queueName);
    }
}

