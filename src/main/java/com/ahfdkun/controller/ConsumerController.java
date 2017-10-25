package com.ahfdkun.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.ahfdkun.command.UserCommand;
import com.netflix.hystrix.HystrixCommand.Setter;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;

import rx.Observable;
import rx.functions.Action1;

@RestController
public class ConsumerController {

	@Autowired
	HelloService helloService;

	/*@RequestMapping(value = "/ribbon-consumer", method = RequestMethod.GET)
	public String helloConsumer() {
		return helloService.helloService();
	}*/
	
	/*@RequestMapping(value = "/ribbon-consumer", method = RequestMethod.GET)
	public String helloConsumer() throws Exception {
		Future<String> future = helloService.helloServiceAsync();
		return future.get(10, TimeUnit.SECONDS);
	}*/
	
	@Autowired
	RestTemplate restTemplate;

	/*@RequestMapping(value = "/ribbon-consumer", method = RequestMethod.GET)
	public String helloConsumer() throws Exception {
		// 只有订阅后才会执行
		Observable<String> observable = new UserCommand(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ABC")).andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withExecutionTimeoutInMilliseconds(10000)), restTemplate).toObservable();
		final String[] s = new String[1];
		observable.subscribe(new Action1<String>() {
			@Override
			public void call(String t) {
				s[0] = t;
			}
		});
		return s[0];
	}*/
	
	@RequestMapping(value = "/ribbon-consumer", method = RequestMethod.GET)
	public String helloConsumer() throws Exception {
		Observable<String> observable = helloService.helloServiceObservable();
		final String[] s = new String[1];
		observable.subscribe(new Action1<String>() {
			@Override
			public void call(String t) {
				s[0] = t;
			}
		});
		return s[0];
	}
	
}
