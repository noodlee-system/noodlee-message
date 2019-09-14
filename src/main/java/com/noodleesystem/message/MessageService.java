package com.noodleesystem.message;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import serilogj.Log;

@SpringBootApplication

public class MessageService {
    public static void main(String[] args) {
        SpringApplication.run(MessageService.class, args);

        Log.information("{serviceName} is running...", "Message Service");
    }
}
