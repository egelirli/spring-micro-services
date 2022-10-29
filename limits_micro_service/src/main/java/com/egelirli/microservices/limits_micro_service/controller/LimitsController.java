package com.egelirli.microservices.limits_micro_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.egelirli.microservices.limits_micro_service.bean.Limits;
import com.egelirli.microservices.limits_micro_service.config.Configuration;

@RestController
public class LimitsController {
		
	    @Autowired
		Configuration config;
		
	   @GetMapping("/limits")
	   public Limits retriveLimits() {
		      return new Limits(config.getMinimum(), config.getMaximum());
		      //return new Limits(1,1000);
	   }
	
}
