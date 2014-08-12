package com.vasa.scheduling.interfaces.web;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.vasa.scheduling.domain.User;
import com.vasa.scheduling.enums.State;
import com.vasa.scheduling.services.UserService;

@RequestMapping("/user/modify")
@Controller
public class UserModifyController extends DefaultHandlerController{
	
	@Autowired
	private UserService mr;
	
	@RequestMapping(method = RequestMethod.GET)
	public String home(Model model, HttpServletRequest request) {
		
		User user = verifyUser(request.getSession());
		model.addAttribute("user", user);
		
		if(user== null){
			return "login";
		}
		
		//User user = mr.findByUserName(username);
		model.addAttribute("modifyuser", user);
		model.addAttribute("states", State.getDisplayNames());
		return "user/modify";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String save(Model model, HttpServletRequest request) {
		
		User user = verifyUser(request.getSession());
		
		if(user== null){
			return "login";
		}
		
		user.setFirstName(request.getParameter("firstName"));
		user.setLastName(request.getParameter("lastName"));
		user.setEmailAddress(request.getParameter("emailAddress"));
		user.setAddress1(request.getParameter("address1"));
		user.setAddress2(request.getParameter("address2"));
		user.setCity(request.getParameter("city"));
		user.setState(request.getParameter("state"));
		user.setPostalCode(request.getParameter("postalCode"));
		
		mr.save(user);
		
		return "user/home";
	}
	
}
