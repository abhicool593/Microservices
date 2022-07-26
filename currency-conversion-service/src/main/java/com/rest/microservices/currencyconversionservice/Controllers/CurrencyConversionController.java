package com.rest.microservices.currencyconversionservice.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.rest.microservices.currencyconversionservice.Entities.CurrencyConversion;
import com.rest.microservices.currencyconversionservice.Proxy.CurrencyConversionProxy;

@RestController
public class CurrencyConversionController {
	@Autowired
	private CurrencyConversionProxy proxy;
	
  @GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
  public CurrencyConversion calculateCurrencyConversion(@PathVariable String from,@PathVariable String to,
		  @PathVariable BigDecimal quantity) {
	  //Have to create the Uri variables for restTemplate
	  Map<String,String> uriVariables = new HashMap<>();
	  uriVariables.put("from", from);
	  uriVariables.put("to", to);
	  //Here the structure of CurrencyConversion is matching with CurrencyExchange and hence field will be mapped automatically
	  ResponseEntity<CurrencyConversion> responseEntity=new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}", CurrencyConversion.class,
			  uriVariables);
	  CurrencyConversion cc=responseEntity.getBody();
	 return new CurrencyConversion(cc.getId(),from,to,cc.getConversionMultiple(),quantity,quantity.multiply(cc.getConversionMultiple()),cc.getEnvironment()+" "+"rest template");
  }
  
  @GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
  public CurrencyConversion calculateCurrencyConversionFeign(@PathVariable String from,@PathVariable String to,
		  @PathVariable BigDecimal quantity) {
	       CurrencyConversion cc = proxy.retriveCurrencyExchange(from, to);
		   return new CurrencyConversion(cc.getId(), from, to, cc.getConversionMultiple(), quantity, quantity.multiply(cc.getConversionMultiple()), cc.getEnvironment()+" "+"feign");	  
  }
}
