package com.vasa.scheduling.interfaces.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.vasa.scheduling.domain.User;
import com.vasa.scheduling.services.EmailService;
import com.vasa.scheduling.services.UserService;

@RequestMapping("/user/forgotpassword")
@Controller
public class UserForgotPasswordController extends DefaultHandlerController{
	
	@Autowired
	private UserService mr;
	
	@Autowired
	private EmailService es;
	
	@RequestMapping(method = RequestMethod.GET)
	public String modify(Model model, HttpServletRequest request) {
		
		return "user/forgotpassword";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String save(Model model, HttpServletRequest request) {
		
		
		String username = request.getParameter("username");
		User user = mr.findByUserName(username);
		
		if(user==null){
			model.addAttribute("loginerror", "Username not valid.");
		}else{
			String emailAddress = user.getEmailAddress();
			String message = "You are receiving this email because someone submitted the forgot password form on the VASA Scheduling website.<br /><br />Your username is "+user.getUserName()+"<br />Your password is "+user.getPassword();
			try{
				es.sendEmail(emailAddress, "Forgot Password", message);
				model.addAttribute("loginerror", "Your Password has been emailed to "+emailAddress);
			}catch(Exception e){
				model.addAttribute("loginerror", e.getCause() +": "+e.getMessage());
			}
		}
		
				
		return "login";
	}
}
