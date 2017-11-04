package com.ahfdkun.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.ahfdkun.command.UserCollapserCommand;
import com.ahfdkun.command.UserGetCommand;
import com.ahfdkun.command.UserPostCommand;
import com.ahfdkun.domain.User;
import com.ahfdkun.service.HelloService;
import com.ahfdkun.service.UserService;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;

@RestController
public class ConsumerController {
	
	@Autowired
	HelloService helloService;

	@RequestMapping(value = "/ribbon-consumer", method = RequestMethod.GET)
	public String helloConsumer() {
		HystrixRequestContext.initializeContext();
		helloService.helloService();
		helloService.helloService();
		return helloService.helloService();
	}
	
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
		final Object lock = new Object(); 
		observable.subscribe(new Action1<String>() {
			@Override
			public void call(String t) {
				s[0] = t;
				synchronized (lock) {
					lock.notify();
				}
			}
		});
		synchronized (lock) {
			if (s[0] == null) {
				s[0] = "error1";
				lock.wait(5000);
			}
		}
		return s[0];
	}*/
	
	/*@RequestMapping(value = "/ribbon-consumer", method = RequestMethod.GET)
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
	}*/
	
	/*@RequestMapping(value = "/ribbon-consumer", method = RequestMethod.GET)
	public String helloConsumer() throws Exception {
		// 只有订阅后才会执行
		Observable<String> observable = new UserObservableCommand(com.netflix.hystrix.HystrixObservableCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ABC")).andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withExecutionTimeoutInMilliseconds(10000)), restTemplate).toObservable();
		final String[] s = new String[1];
		observable.subscribe(new Action1<String>() {
			@Override
			public void call(String t) {
				s[0] = t;
			}
		});
		return s[0];
	}*/
	
	@RequestMapping(value = "/ribbon-consumer/{id}", method = RequestMethod.GET)
	public String user(@PathVariable Long id) {
		HystrixRequestContext.initializeContext();
		new UserGetCommand(restTemplate, id).execute();
		new UserGetCommand(restTemplate, id).execute();
		// 只有订阅后才会执行
		return new UserGetCommand(restTemplate, id).execute();
	}
	
	@RequestMapping(value = "/ribbon-consumer/{id}", method = RequestMethod.POST)
	public String postUser(@PathVariable Long id) throws Exception {
		// 只有订阅后才会执行
		return new UserPostCommand(restTemplate, id).execute();
	}
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value = "/hystrixCollapser", method = RequestMethod.GET)
	public List<User> hystrixCollapser() throws Exception {
		HystrixRequestContext context = HystrixRequestContext.initializeContext();
		// 只有订阅后才会执行
		Future<User> user1 = new UserCollapserCommand(userService, 1L).queue();
		Future<User> user2 = new UserCollapserCommand(userService, 2L).queue();
		Future<User> user3 = new UserCollapserCommand(userService, 3L).queue();
		Future<User> user4 = new UserCollapserCommand(userService, 4L).queue();
		Future<User> user5 = new UserCollapserCommand(userService, 5L).queue();
		List<User> users = new ArrayList<>();
		users.add(user1.get());
		users.add(user2.get());
		users.add(user3.get());
		users.add(user4.get());
		users.add(user5.get());
		context.close();
		return users;
	}
	
}
