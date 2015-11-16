package com.vasa.scheduling.interfaces.web;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.vasa.scheduling.domain.User;
import com.vasa.scheduling.enums.State;
import com.vasa.scheduling.enums.Status;
import com.vasa.scheduling.enums.UserType;
import com.vasa.scheduling.services.EmailService;
import com.vasa.scheduling.services.UserService;

@RequestMapping("/user/requestaccount")
@Controller
public class UserRequestAccountController extends DefaultHandlerController{
	
	@Autowired
	private UserService mr;
	
	@Autowired
	private EmailService es;
	
	@RequestMapping(method = RequestMethod.GET)
	public String modify(Model model, HttpServletRequest request) {
		
		model.addAttribute("states", State.getDisplayNames());
		return "user/requestaccount";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String save(Model model, HttpServletRequest request) {
		
		User user = new User();
		
		user.setStatus(Status.REQUEST);
		user.setUserType(UserType.USER);
		
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
		
		user.setLastLogin(new Date());
		user.setLoginFailures(0);
		user.setMemberSince(new Date());
		
		mr.save(user);
		
		String sport = request.getParameter("sport");
		String comments = request.getParameter("comments");
		
		String emailAddress = "andrewcsikes@gmail.com";
		StringBuffer message = new StringBuffer();
		message.append("Make sure the following are answered ... If it's for a team, where do you play and what age group?");
		message.append("      New User has Requested an account. "+user.getFirstName()+" "+user.getLastName());
		message.append(" email address: "+user.getEmailAddress());
		message.append("      Sport: " + sport + "      Comments: "+comments);
		
		
		try{
			es.sendEmail(emailAddress, "New User Request", message.toString());
			model.addAttribute("loginerror", "Your request has been emailed to the Scheduler.");
		}catch(Exception e){
			model.addAttribute("loginerror", e.getCause() +": "+e.getMessage());
		}
				
		return "login";
	}
}
