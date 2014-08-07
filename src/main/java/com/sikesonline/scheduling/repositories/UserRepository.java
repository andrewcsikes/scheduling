package com.sikesonline.scheduling.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.sikesonline.scheduling.domain.User;

@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Integer>{

	User findByUserName(String username);
	
}