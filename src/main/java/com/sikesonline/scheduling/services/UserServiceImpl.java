package com.sikesonline.scheduling.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sikesonline.scheduling.domain.User;
import com.sikesonline.scheduling.repositories.UserRepository;

@Repository
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository repository;
	
	@Transactional
	public User save(User member) {
		return repository.save(member);
	}
	
	public User findByUserName(String username) {
		return repository.findByUserName(username);
	}
	
}
