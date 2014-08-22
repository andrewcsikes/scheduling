package com.vasa.scheduling.interfaces.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.vasa.scheduling.domain.User;
import com.vasa.scheduling.enums.State;
import com.vasa.scheduling.enums.UserType;
import com.vasa.scheduling.enums.Status;
import com.vasa.scheduling.services.UserService;

@RequestMapping("/user/modify")
@Controller
public class UserModifyController extends DefaultHandlerController{
	
	@Autowired
	private UserService mr;
	
	@RequestMapping(method = RequestMethod.GET)
	public String modify(@RequestParam(required=false, value="user") Integer userId,
			Model model, HttpServletRequest request) {
		
		User user = verifyUser(request.getSession());
		model.addAttribute("user", user);
		
		if(user== null){
			return "login";
		}
		
		
		User modifyUser = null;
		if(userId != null){
			modifyUser = mr.findById(userId);
		}else{
			modifyUser = user;
		}
		
		model.addAttribute("modifyuser", modifyUser);
		model.addAttribute("states", State.getDisplayNames());
		return "user/modify";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String save(Model model, HttpServletRequest request) {
		
		
		User realUser = verifyUser(request.getSession());
		
		if(realUser== null){
			return "login";
		}
		
		User user = null;
		
		if(request.getParameter("id") != null){
			user = mr.findById(Integer.valueOf(request.getParameter("id")));
		}
		
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
		
		mr.save(user);
		
		return "user/home";
	}
	
}
