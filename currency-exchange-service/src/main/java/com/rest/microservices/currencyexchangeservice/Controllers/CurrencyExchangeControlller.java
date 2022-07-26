package com.rest.microservices.currencyexchangeservice.Controllers;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.rest.microservices.currencyexchangeservice.Model.CurrencyExchange;
import com.rest.microservices.currencyexchangeservice.Repositories.CurrencyExchangeRepository;

@RestController
public class CurrencyExchangeControlller {
    
	@Autowired
	private Environment environment;
	
	@Autowired
	private CurrencyExchangeRepository cer;
	
	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public CurrencyExchange getExchangevalue(@PathVariable String from,@PathVariable String to) {
//		CurrencyExchange currencyExchange= new CurrencyExchange(1000L,from,to,BigDecimal.valueOf(50));
		
		CurrencyExchange currencyExchange = cer.findByFromAndTo(from, to);
		if(currencyExchange==null) {
			throw new RuntimeException("Unable to find any data for "+from +" to "+to);
		}
		currencyExchange.setEnvironment(environment.getProperty("local.server.port"));
		return currencyExchange;
	}
}
