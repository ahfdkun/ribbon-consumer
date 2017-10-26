package com.ahfdkun.command;

import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.exception.HystrixBadRequestException;

public class UserCommand extends HystrixCommand<String> {

	private RestTemplate restTemplate;

	public UserCommand(Setter setter, RestTemplate restTemplate) {
		super(setter);
		this.restTemplate = restTemplate;
	}

	@Override
	protected String run() throws Exception {
		try {
			return restTemplate.getForObject("http://HELLO-SERVICE/hello1", String.class);
		} catch (Exception e) {
			throw new Exception("xxxx");
		}
	}

	// 实现服务降级逻辑
	@Override
	protected String getFallback() {
		System.out.println(getExecutionException());
		return "error";
	}

}
