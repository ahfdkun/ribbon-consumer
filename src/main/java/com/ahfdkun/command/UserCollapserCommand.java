package com.ahfdkun.command;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.ahfdkun.domain.User;
import com.ahfdkun.service.UserService;
import com.netflix.hystrix.HystrixCollapser;
import com.netflix.hystrix.HystrixCollapserKey;
import com.netflix.hystrix.HystrixCollapserProperties;
import com.netflix.hystrix.HystrixCommand;

public class UserCollapserCommand extends HystrixCollapser<List<User>, User, Long> {

	private UserService userService;
	private Long userId;

	public UserCollapserCommand(UserService userService, Long userId) {
		super(Setter.withCollapserKey(HystrixCollapserKey.Factory.asKey("userCollasperCommand"))
				.andCollapserPropertiesDefaults(HystrixCollapserProperties.Setter().withTimerDelayInMilliseconds(5*1000)));
		this.userService = userService;
		this.userId = userId;
	}

	@Override
	public Long getRequestArgument() {
		return userId;
	}

	@Override
	protected HystrixCommand<List<User>> createCommand(Collection<com.netflix.hystrix.HystrixCollapser.CollapsedRequest<User, Long>> requests) {
		List<Long> userIds = new ArrayList<Long>(requests.size());
		userIds.addAll(requests.stream().map(CollapsedRequest::getArgument).collect(Collectors.toList()));
		return new UserBatchCommand(userService, userIds);
	}

	@Override
	protected void mapResponseToRequests(List<User> batchResponse, Collection<com.netflix.hystrix.HystrixCollapser.CollapsedRequest<User, Long>> requests) {
		int index = 0;
		for (CollapsedRequest<User, Long> collapsedRequest : requests) {
			User user = batchResponse.get(index++);
			collapsedRequest.setResponse(user);
		}
	}

}
