package com.vasa.scheduling.interfaces.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.vasa.scheduling.domain.User;
import com.vasa.scheduling.services.UserService;

/**
 * Handles requests for the application home page.
 */
@RequestMapping("/user/help")
@Controller
public class UserHelpController extends DefaultHandlerController{
	
	@Autowired private UserService service;
	
	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model, HttpServletRequest request) {
		
		User user = verifyUser(request.getSession());
		model.addAttribute("user", user);
		
		if(user== null){
			return "login";
		}

	    return "user/help";
	}
	
}
