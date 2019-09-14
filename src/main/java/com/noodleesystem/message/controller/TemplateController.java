package com.noodleesystem.message.controller;

import com.noodleesystem.message.repository.UserRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TemplateController {

    @Autowired
    private UserRepository usersRepository;

    @Autowired
    RabbitTemplate rabbitTemplate;
}
