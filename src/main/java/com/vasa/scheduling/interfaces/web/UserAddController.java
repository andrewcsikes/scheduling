package com.vasa.scheduling.interfaces.web;

import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.vasa.scheduling.domain.User;
import com.vasa.scheduling.enums.State;
import com.vasa.scheduling.enums.UserType;
import com.vasa.scheduling.enums.Status;
import com.vasa.scheduling.services.EmailService;
import com.vasa.scheduling.services.UserService;

@RequestMapping("/user/add")
@Controller
public class UserAddController extends DefaultHandlerController{
	
	@Autowired
	private UserService mr;
	
	@Autowired
	private EmailService es;
	
	@RequestMapping(method = RequestMethod.GET)
	public String modify(Model model, HttpServletRequest request) {
		
		User user = verifyUser(request.getSession());
		model.addAttribute("user", user);
		
		if(user== null){
			return "login";
		}
		
		model.addAttribute("states", State.getDisplayNames());
		return "user/add";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String save(Model model, HttpServletRequest request) {
		
		
		User realUser = verifyUser(request.getSession());
		
		if(realUser== null){
			return "login";
		}
		
		User user = new User();
				
		if(request.getParameter("userType") != null){
			user.setUserType(UserType.toEnumFromCode(Integer.valueOf(request.getParameter("userType"))));
		}
		if(request.getParameter("status") != null){
			user.setStatus(Status.toEnumFromCode(Integer.valueOf(request.getParameter("status"))));
		}
		
		user.setUserName(request.getParameter("username"));
		user.setPassword(request.getParameter("password"));
		user.setFirstName(request.getParameter("firstName"));
		user.setLastName(request.getParameter("lastName"));
		user.setEmailAddress(request.getParameter("emailAddress"));
		user.setAddress1(request.getParameter("address1"));
		user.setAddress2(request.getParameter("address2"));
		user.setCity(request.getParameter("city"));
		user.setState(request.getParameter("state"));
		user.setPhone(request.getParameter("phone"));
		user.setPostalCode(request.getParameter("postalCode"));
		
		Calendar today = Calendar.getInstance();
		Date d = new Date();
		today.setTime(d);
		today.set(Calendar.HOUR_OF_DAY, 0);
		today.set(Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);
				
		user.setLastLogin(today.getTime());
		user.setLoginFailures(0);
		user.setMemberSince(new Date());
		
		mr.save(user);
		
		String emailAddress = user.getEmailAddress();
		String message = "New Account has been setup for you on the VASA Field Scheduling Website - http://scheduling.vasayouthsports.com" +
				"<br /><br />Your username is "+user.getUserName()+"<br />Your password is "+user.getPassword();
		message += " email address: "+user.getEmailAddress();
		try{
			es.sendEmail(emailAddress, "New User Account for VASA Field Scheduling", message);
			model.addAttribute("loginerror", "Your request has been emailed to the Scheduler.");
		}catch(Exception e){
			model.addAttribute("loginerror", e.getCause() +": "+e.getMessage());
		}
		
		return "user/home";
	}
	
}
