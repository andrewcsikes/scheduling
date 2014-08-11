package com.vasa.scheduling.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.vasa.scheduling.domain.User;

@Service
public interface UserService{

	User save(User member);
	User findByUserName(String username);
	List<User> findAll();

}
