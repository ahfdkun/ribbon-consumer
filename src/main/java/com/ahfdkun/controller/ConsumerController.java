package com.ahfdkun.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ConsumerController {

	private static final String HELLO_SERVICE_URL = "http://HELLO-SERVICE/hello";
	@Autowired
	RestTemplate restTemplate;

	@RequestMapping(value = "/ribbon-consumer", method = RequestMethod.GET)
	public String helloConsumer() {
//		UriComponents uriComponents = UriComponentsBuilder.fromUriString("").build().expand("").encode();
//		uriComponents.toUri();
		return restTemplate.getForEntity(HELLO_SERVICE_URL, String.class).getBody();
	}
}
