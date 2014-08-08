package com.vasa.scheduling.services;

import com.vasa.scheduling.domain.User;


public interface UserService{

	User save(User member);
	User findByUserName(String username);

}
