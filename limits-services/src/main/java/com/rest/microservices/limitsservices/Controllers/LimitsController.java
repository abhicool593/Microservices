package com.rest.microservices.limitsservices.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rest.microservices.limitsservices.Configurations.Configuration;
import com.rest.microservices.limitsservices.Model.Limits;

@RestController
@RequestMapping("/")
public class LimitsController {

	@Autowired
	private Configuration configuration;
	@GetMapping("/getLimits")
	public Limits getLimits() {
		//return new Limits(1,1000);//get these values from configuration
		return new Limits(configuration.getMinimum(),configuration.getMaximum());
		
	}
}
