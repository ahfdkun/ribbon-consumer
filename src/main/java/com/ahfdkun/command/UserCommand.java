package com.ahfdkun.command;

import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.HystrixCommand;

public class UserCommand extends HystrixCommand<String> {

	private RestTemplate restTemplate;

	public UserCommand(Setter setter, RestTemplate restTemplate) {
		super(setter);
		this.restTemplate = restTemplate;
	}

	@Override
	protected String run() throws Exception {
		return restTemplate.getForObject("http://HELLO-SERVICE/hello", String.class);
	}

	@Override
	protected String getFallback() {
		return "error";
	}

}
