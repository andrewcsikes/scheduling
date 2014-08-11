package com.vasa.scheduling.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import com.vasa.scheduling.domain.User;
import com.vasa.scheduling.repositiories.UserRepository;

@Service("userService")
//@ComponentScan("com.vasa.scheduling.repositiories")
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository repository;
	
	@Override
	public User save(User member) {
		return repository.save(member);
	}
	
	@Override
	public User findByUserName(String username) {
		return repository.findByUserName(username);
	}

	@Override
	public List<User> findAll() {
		return repository.findAll();
	}
	
}
