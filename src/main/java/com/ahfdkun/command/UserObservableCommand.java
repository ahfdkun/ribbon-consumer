package com.ahfdkun.command;

import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.HystrixObservableCommand;

import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Subscriber;

public class UserObservableCommand extends HystrixObservableCommand<String> {

	private RestTemplate restTemplate;

	public UserObservableCommand(Setter setter, RestTemplate restTemplate) {
		super(setter);
		this.restTemplate = restTemplate;
	}

	@Override
	protected Observable<String> construct() {
		return Observable.create(new OnSubscribe<String>(){
			@Override
			public void call(Subscriber<? super String> observer) {
				try {
					if(!observer.isUnsubscribed()) {
						String s = restTemplate.getForObject("http://HELLO-SERVICE/hello", String.class);
						observer.onNext(s);
						observer.onCompleted();
					}
				} catch (Exception e) {
					observer.onError(e);
				}
			}
		});
	}

	// 实现服务降级逻辑
	@Override
	protected Observable<String> resumeWithFallback() {
		return Observable.create(new OnSubscribe<String>(){
			@Override
			public void call(Subscriber<? super String> observer) {
				observer.onNext("error");
				observer.onCompleted();
			}
		});
	}

}
