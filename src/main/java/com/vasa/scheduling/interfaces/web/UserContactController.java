package com.vasa.scheduling.interfaces.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.vasa.scheduling.domain.Team;
import com.vasa.scheduling.domain.User;
import com.vasa.scheduling.services.EmailService;
import com.vasa.scheduling.services.ScheduleService;
import com.vasa.scheduling.services.TeamService;
import com.vasa.scheduling.services.UserService;

@RequestMapping("/user/contact")
@Controller
public class UserContactController extends DefaultHandlerController{
	
	@Autowired private UserService userService;
	@Autowired private TeamService service;
	@Autowired private EmailService es;
	@Autowired private ScheduleService scheduleService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String modify(Model model, HttpServletRequest request) {
		String username=request.getParameter("username");
		model.addAttribute("username", username);
		String name=request.getParameter("name");
		model.addAttribute("name", name);
		return "user/contact";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String save(Model model, HttpServletRequest request) {
		
		
		User realUser = verifyUser(request.getSession());
		
		if(realUser== null){
			return "login";
		}
		
		String message = request.getParameter("message");
		message = realUser.getFirstName() + " " + realUser.getLastName() +" sent you a message through the VASA field scheduling site. MESSAGE="+message; 
				
		String username = request.getParameter("username");
		User user = userService.findByUserName(username);
		
		String emailAddress = user.getEmailAddress();
		try{
			es.sendEmail(emailAddress, realUser.getEmailAddress(), "Forgot Password", message);
			model.addAttribute("loginerror", "Your Password has been emailed to "+emailAddress);
		}catch(Exception e){
			model.addAttribute("loginerror", e.getCause() +": "+e.getMessage());
		}
		
		List<Team> teams = service.findActive();
	    model.addAttribute("teams", teams);
	    
	    model.addAttribute("coaches", userService.findAllCoaches());
		model.addAttribute("sports", service.findAllSports());
		model.addAttribute("seasons", scheduleService.findActiveSeasons());
		model.addAttribute("agegroups", service.findAllAgegroups());
		
		return "team/list";
	}
}
