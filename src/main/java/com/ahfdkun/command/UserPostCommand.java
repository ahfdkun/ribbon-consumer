package com.ahfdkun.command;

import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;

public class UserPostCommand extends HystrixCommand<String> {

	private RestTemplate restTemplate;
	private Long id;

	public UserPostCommand(RestTemplate restTemplate, Long id) {
		super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("GetSetGet")));
		this.restTemplate = restTemplate;
		this.id = id;
	}

	@Override
	protected String run() throws Exception {
		try {
			String x = restTemplate.postForObject("http://HELLO-SERVICE/users", id, String.class);
			UserGetCommand.flushCache(id);
			return x;
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
