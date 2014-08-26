package com.vasa.scheduling.interfaces.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.vasa.scheduling.domain.User;
import com.vasa.scheduling.services.UserService;

// TODO: Add ability to select current sport
// TODO: Forgot Password

@Controller
@RequestMapping("/user/list")
//@ComponentScan("com.vasa.scheduling.services")
public class UserListController extends DefaultHandlerController {

	@Autowired private UserService service;
	
	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model, HttpServletRequest request) {
		
		User user = verifyUser(request.getSession());
		model.addAttribute("user", user);
		
		if(user== null){
			return "login";
		}
				
		List<User> users = service.findAll();	
	    model.addAttribute("users", users);
	    return "user/list";
	}
}