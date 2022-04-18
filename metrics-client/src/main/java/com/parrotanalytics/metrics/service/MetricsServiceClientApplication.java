package com.parrotanalytics.metrics.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter;

@ComponentScan(basePackages =
		{
				"com.parrotanalytics.metrics.service.services","com.parrotanalytics.metrics.service.controller",
				"com.parrotanalytics.metrics.service","com.parrotanalytics.metrics.model.mapper"
		})
@SpringBootApplication
public class MetricsServiceClientApplication {
	@Bean
	ProtobufHttpMessageConverter protobufHttpMessageConverter() {
		return new ProtobufHttpMessageConverter();
	}

	public static void main(String[] args) {
		SpringApplication.run(MetricsServiceClientApplication.class, args);
	}

}
