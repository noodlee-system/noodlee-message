package com.noodleesystem.message.worker;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.noodleesystem.message.config.EmailClientConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class EmailSenderWorker {
    private ObjectMapper mapper;
    private static EmailClientConfig config;


    public EmailSenderWorker() {
        this.mapper = new ObjectMapper();

    }

    @RabbitListener(queues = "emails_queue")
    public void sendEmail(String emailJsonString){
    }
}
