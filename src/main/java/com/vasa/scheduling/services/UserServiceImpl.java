package com.vasa.scheduling.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import com.vasa.scheduling.domain.GlobalMessage;
import com.vasa.scheduling.domain.Team;
import com.vasa.scheduling.domain.User;
import com.vasa.scheduling.enums.Status;
import com.vasa.scheduling.enums.UserType;
import com.vasa.scheduling.repositiories.MessageRepository;
import com.vasa.scheduling.repositiories.TeamRepository;
import com.vasa.scheduling.repositiories.UserRepository;

@Service("userService")
//@ComponentScan("com.vasa.scheduling.repositiories")
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private TeamRepository teamRepo;
	
	@Autowired
	private MessageRepository messageRepo;
	
	@Override
	public User save(User member) {
		return userRepo.save(member);
	}
	
	@Override
	public User findByUserName(String username) {
		return userRepo.findByUserName(username);
	}
	
	@Override
	public User findById(Integer id) {
		return userRepo.findById(id);
	}

	@Override
	public List<User> findAll() {
		return userRepo.findAll();
	}

	@Override
	public Team getTeam(User user) {
		return teamRepo.findByCoach(user);
	}

	@Override
	public List<User> findAllCoaches() {
		List <User> coaches = teamRepo.findByUserType(UserType.COACH);
		coaches.addAll(teamRepo.findByUserType(UserType.ADMIN));
		coaches.addAll(teamRepo.findByUserType(UserType.COMMISSIONER));
		
		List<User> users = new ArrayList<User>();
		for(User u : coaches){
			if(u.getStatus() == Status.ACTIVE){
				users.add(u);
			}
		}
		
		return users;
	}

	@Override
	public User getLastActive() {
		Date maxDate = null;
		User user = null;
		
		List<User> users = userRepo.findAll();
		for(User u : users){
			if(maxDate == null || u.getLastLogin().after(maxDate) && u.getUserType() != UserType.ADMIN){
				maxDate=u.getLastLogin();
				user=u;
			}
		}
		
		return user;
	}

	@Override
	public String getGlobalMessage() {
		List<GlobalMessage> message = messageRepo.findAll();
		if(message.size()>0){
			return message.get(0).getMessage();
		}
		return null;
	}

	@Override
	public void setGlobalMessage(String message) {
		messageRepo.deleteAll();
		if(message == null || message.length()<=0){
			
		}else{
			GlobalMessage m = new GlobalMessage();
			m.setMessage(message);
			messageRepo.save(m);
		}
	}
	
}
