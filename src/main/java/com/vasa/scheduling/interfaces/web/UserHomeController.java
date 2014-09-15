package com.vasa.scheduling.interfaces.web;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.vasa.scheduling.domain.User;
import com.vasa.scheduling.enums.Status;
import com.vasa.scheduling.services.UserService;

/**
 * Handles requests for the application home page.
 */
@RequestMapping("/user/home")
@Controller
public class UserHomeController extends DefaultHandlerController{
	
	// TODO: Add request for Account
	
	@Autowired
	private UserService mr;
	
	private static final Logger logger = LoggerFactory.getLogger(UserHomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(method = RequestMethod.POST)
	public String home(@RequestParam(required=true, value="username") String username,
			@RequestParam(required=true, value="password") String password,
			Model model, HttpServletRequest request) {
		
		User user = mr.findByUserName(username);
		
		if(user != null){
			if(user.getPassword().equals(password)){
				if(user.getStatus().equals(Status.ACTIVE)){
					user.setLastLogin(new Date());
					user.setLoginFailures(0);
					mr.save(user);
					request.getSession().setAttribute("user", user);
					return "user/home";
				}else{
					model.addAttribute("loginerror", "user "+username+" is not active." );
					return "login";
				}
			}else{
				user.setLoginFailures(user.getLoginFailures()+1);
				mr.save(user);
				model.addAttribute("loginerror", "Incorrect password for "+username+".");
				return "login";
			}
		}else{
			model.addAttribute("loginerror", "Invalid username." );
			return "login";
		}
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String home(Model model, HttpServletRequest request) {
		
		User user = verifyUser(request.getSession());
		
		if(user == null){
			return "login";
		}
		
		model.addAttribute("user", user);
		return "user/home";
	}
	
}
