package com.noodleesystem.message.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    private static final String queueName = "emails_queue";

    @Bean
    public Queue emailsQueue() {
        return new Queue(queueName);
    }
}

