package com.parrotanalytics.metrics.service;

import com.parrotanalytics.api.data.repo.api.cache.MetadataLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

@SpringBootApplication
public class MetricsServiceApplication  extends SpringBootServletInitializer implements
		ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private MetadataLoader metadataLoader;


	/**
	 * This method is called during Spring's startup.
	 *
	 * @param event Event raised when an ApplicationContext gets initialized or
	 * refreshed.
	 */
	@Override
	public void onApplicationEvent(final ContextRefreshedEvent event) {
		metadataLoader.initialize();
	}

	public static void main(String[] args) {
		SpringApplication.run(MetricsServiceApplication.class, args);
	}

}
