package com.ahfdkun.service;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ahfdkun.domain.User;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	RestTemplate restTemplate;

	@Override
	public User find(Long id) {
		return restTemplate.getForObject("http://HELLO-SERVICE/users1/{1}", User.class, id);
	}

	@Override
	public List<User> findAll(List<Long> ids) {
		User[] users = restTemplate.getForObject("http://HELLO-SERVICE/users1?ids={1}", User[].class, StringUtils.join(ids, ","));
		return Arrays.asList(users);
	}

}
