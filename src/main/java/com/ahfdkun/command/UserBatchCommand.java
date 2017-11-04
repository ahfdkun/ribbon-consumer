package com.ahfdkun.command;

import java.util.ArrayList;
import java.util.List;

import com.ahfdkun.domain.User;
import com.ahfdkun.service.UserService;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

public class UserBatchCommand extends HystrixCommand<List<User>> {

	private UserService userService;
	List<Long> userIds;

	public UserBatchCommand(UserService userService, List<Long> userIds) {
		super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("userServiceCommand")));
		this.userIds = userIds;
		this.userService = userService;
	}

	@Override
	protected List<User> run() throws Exception {
		return userService.findAll(userIds);
	}

	@Override
	protected List<User> getFallback() {
		return new ArrayList<User>();
	}

}
