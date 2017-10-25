package com.ahfdkun.controller;

import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.ObservableExecutionMode;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;

import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Subscriber;

@Service
public class HelloService {

	private static final String HELLO_SERVICE_URL = "http://HELLO-SERVICE/hello";
	
	@Autowired
	RestTemplate restTemplate;

	// 同步
	@HystrixCommand(fallbackMethod = "helloFallback")
	public String helloService() {
		return restTemplate.getForEntity(HELLO_SERVICE_URL, String.class).getBody();
	}
	
	
	// 异步
	@HystrixCommand(fallbackMethod = "helloFallback")
	public Future<String> helloServiceAsync() {
		return new AsyncResult<String>() {
			public String invoke() {
				return restTemplate.getForEntity(HELLO_SERVICE_URL, String.class).getBody();
			}
		};
	}
	
	// observableExecutionMode参数设置observe()（调用observe方法就会执行call方法）或者toObServable()（被订阅时才执行call方法）模式
	@HystrixCommand(fallbackMethod = "helloFallback", observableExecutionMode = ObservableExecutionMode.LAZY)
	public Observable<String> helloServiceObservable() {
		return Observable.create(new OnSubscribe<String>(){
			@Override
			public void call(Subscriber<? super String> observer) {
				try {
					if(!observer.isUnsubscribed()) {
						String s = restTemplate.getForObject(HELLO_SERVICE_URL, String.class);
						observer.onNext(s);
						observer.onCompleted();
					}
				} catch (Exception e) {
					observer.onError(e);
				}
			}
		});
	}
	
	public String helloFallback() {
		return "error";
	}
	
}
