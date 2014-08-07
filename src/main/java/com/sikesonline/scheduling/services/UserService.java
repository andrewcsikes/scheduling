package com.sikesonline.scheduling.services;

import com.sikesonline.scheduling.domain.User;

public interface UserService{

	User save(User member);
	User findByUserName(String username);

}
