package com.noodleesystem.template;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import serilogj.Log;

@SpringBootApplication
@EnableDiscoveryClient
public class TemplateService {
    public static void main(String[] args) {
		SpringApplication.run(TemplateService.class, args);

        Log.information("{serviceName} is running...", "Template Service");
	}
}
