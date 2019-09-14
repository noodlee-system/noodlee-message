package com.noodleesystem.message.controller;

import com.noodleesystem.message.exception.EmptyQueueException;
import com.noodleesystem.message.model.User;
import com.noodleesystem.message.repository.UserRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import serilogj.Log;

import java.text.MessageFormat;
import java.util.List;

@RestController
public class TemplateController {
    final static String queueName = "emails_queue";

    @Autowired
    private UserRepository usersRepository;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @GetMapping("/test")
    List<User> getAllUsers() {
        return usersRepository.findAll();
    }

    @GetMapping("/send")
    String sendToQueue(@RequestParam(value = "message", defaultValue = "Default message") String message) {
        message = "{\"to\":\"robert.suszek@thomas-it.pl\",\"subcjet\":\"Test\",\"body\":\"test e-maila\"}";
        rabbitTemplate.convertAndSend(queueName, message);
        Log.information("{message} message was sent to {queue} queue!", message, queueName);
        return String.format("Message %s sent!", message);
    }

    @GetMapping("/receive")
    public String get() {
        Object messageObject = rabbitTemplate.receiveAndConvert(queueName);

        if (messageObject != null) {
            String message = messageObject.toString();
            Log.information("{message} message was read from {queue} queue!", message, queueName);
            return message;
        } else {
            String errorMessage = MessageFormat.format("No message in queue {0}!", queueName);
            throw new EmptyQueueException(errorMessage);
        }
    }
}
