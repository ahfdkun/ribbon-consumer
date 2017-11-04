package com.ahfdkun.service;

import java.util.List;

import com.ahfdkun.domain.User;

public interface UserService {

	public User find(Long id);
	
	public List<User> findAll(List<Long> ids);
}
