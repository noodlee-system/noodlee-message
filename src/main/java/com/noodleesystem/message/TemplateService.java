package com.noodleesystem.message;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import serilogj.Log;

@SpringBootApplication
public class TemplateService {
    public static void main(String[] args) {
		SpringApplication.run(TemplateService.class, args);

        Log.information("{serviceName} is running...", "Message Service");
	}
}
