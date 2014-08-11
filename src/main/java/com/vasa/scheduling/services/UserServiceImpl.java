package com.vasa.scheduling.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.vasa.scheduling.domain.User;
import com.vasa.scheduling.repositiories.UserRepository;

@Repository
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository repository;
	
	@Override
	@Transactional
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
