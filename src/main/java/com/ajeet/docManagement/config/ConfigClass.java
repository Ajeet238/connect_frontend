package com.ajeet.docManagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

public class ConfigClass {

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
