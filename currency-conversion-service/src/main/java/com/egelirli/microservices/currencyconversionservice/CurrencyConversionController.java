package com.egelirli.microservices.currencyconversionservice;

import java.math.BigDecimal;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CurrencyConversionController {
	
	 //@Autowired
	 private CurrencyExchangeProxy  excahngeProxy;
	
	public  CurrencyConversionController(CurrencyExchangeProxy excahngeProxy) {
		this.excahngeProxy = excahngeProxy;
	}
	 
	@GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion calculateCurrencyConversion(
			@PathVariable String from,
			@PathVariable String to,
			@PathVariable BigDecimal quantity
			) {
				
	
		HashMap<String, String> params = new HashMap<>();
		params.put("from",from);
		params.put("to",to);
		
		ResponseEntity<CurrencyConversion> responseEntity = 
				                new RestTemplate().getForEntity(
				                	  "http://localhost:8001/currency-exchange/from/{from}/to/{to}", 
								      CurrencyConversion.class,
								      params);
		
		CurrencyConversion currencyConversion = responseEntity.getBody();
		
		return new CurrencyConversion(currencyConversion.getId(), 
				from, to, quantity, 
				currencyConversion.getExchangeRate(), 
				quantity.multiply(currencyConversion.getExchangeRate()), 
				currencyConversion.getEnvironment()+ " " + "rest template");
	
	}
	
	@GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion calculateCurrencyConversionFeign(
			@PathVariable String from,
			@PathVariable String to,
			@PathVariable BigDecimal quantity
			) {
		
	     CurrencyConversion currencyConversion = excahngeProxy.retrieveExchangeValue(from, to);
		
		return new CurrencyConversion(currencyConversion.getId(), 
				from, to, quantity, 
				currencyConversion.getExchangeRate(), 
				quantity.multiply(currencyConversion.getExchangeRate()), 
				currencyConversion.getEnvironment() + " " + "feign");
		
	}
				
					
}
