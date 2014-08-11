package com.vasa.scheduling.services;

import java.util.List;

import com.vasa.scheduling.domain.User;

public interface UserService{

	User save(User member);
	User findByUserName(String username);
	List<User> findAll();

}
