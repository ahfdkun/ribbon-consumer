package com.ahfdkun.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class HelloService {

	private static final String HELLO_SERVICE_URL = "http://HELLO-SERVICE/hello";
	
	@Autowired
	RestTemplate restTemplate;

	@HystrixCommand(fallbackMethod = "helloFallback")
	public String helloService() {
		return restTemplate.getForEntity(HELLO_SERVICE_URL, String.class).getBody();
	}
	
	public String helloFallback() {
		return "error";
	}
	
}
