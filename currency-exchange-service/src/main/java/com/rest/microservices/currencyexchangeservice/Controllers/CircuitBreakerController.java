package com.rest.microservices.currencyexchangeservice.Controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
public class CircuitBreakerController {
	
	private Logger logger = LoggerFactory.getLogger(CircuitBreakerController.class);

	@GetMapping("/sample-api")
	//@Retry(name="sample-api",fallbackMethod = "hardCodedResponse")
	@CircuitBreaker(name="default",fallbackMethod = "hardCodedResponse")
	public String sampleApi() {
		logger.info("sample api invoked");
		ResponseEntity<String> responseEntity=new RestTemplate().getForEntity("http://localhost:8089/dummy-url",String.class);
		return responseEntity.getBody();
	}
	
	public String hardCodedResponse(Exception ex) {
		
		return "hard-Coded-Response";
	}
}
