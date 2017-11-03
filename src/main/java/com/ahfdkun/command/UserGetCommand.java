package com.ahfdkun.command;

import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixRequestCache;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategyDefault;

public class UserGetCommand extends HystrixCommand<String> {

	private static final HystrixCommandKey GETTER_KEY = HystrixCommandKey.Factory.asKey("CommandKey");

	private RestTemplate restTemplate;
	private Long id;

	public UserGetCommand(RestTemplate restTemplate, Long id) {
		super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("GetSetGet")).andCommandKey(GETTER_KEY));
		this.restTemplate = restTemplate;
		this.id = id;
	}

	@Override
	protected String run() throws Exception {
		try {
			return restTemplate.getForObject("http://HELLO-SERVICE/users/{1}", String.class, id);
		} catch (Exception e) {
			throw new Exception("xxxx");
		}
	}

	// 实现服务降级逻辑
	@Override
	protected String getFallback() {
		return "error";
	}

	@Override
	protected String getCacheKey() { // request级别的缓存
		return String.valueOf(id);
	}

	public static void flushCache(Long id) {
		// 刷新缓存，根据id进行清理
		HystrixRequestCache.getInstance(GETTER_KEY, HystrixConcurrencyStrategyDefault.getInstance())
				.clear(String.valueOf(id));
	}

}
