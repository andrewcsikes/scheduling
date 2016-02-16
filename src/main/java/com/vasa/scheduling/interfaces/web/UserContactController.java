package com.vasa.scheduling.interfaces.web;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.vasa.scheduling.domain.Log;
import com.vasa.scheduling.domain.Season;
import com.vasa.scheduling.domain.Team;
import com.vasa.scheduling.domain.User;
import com.vasa.scheduling.enums.Carrier;
import com.vasa.scheduling.enums.Status;
import com.vasa.scheduling.services.EmailService;
import com.vasa.scheduling.services.ScheduleService;
import com.vasa.scheduling.services.SeasonService;
import com.vasa.scheduling.services.TeamService;
import com.vasa.scheduling.services.UserService;

@RequestMapping("/user")
@Controller
public class UserContactController extends DefaultHandlerController{
	
	@Autowired private UserService userService;
	@Autowired private TeamService service;
	@Autowired private EmailService es;
	@Autowired private ScheduleService scheduleService;
	@Autowired private SeasonService seasonService;
	
	@RequestMapping(value="/contact", method = RequestMethod.GET)
	public String contact(Model model, HttpServletRequest request) {
		String username=request.getParameter("username");
		model.addAttribute("username", username);
		String name=request.getParameter("name");
		model.addAttribute("name", name);
		return "user/contact";
	}
	
	@RequestMapping(value="/contact", method = RequestMethod.POST)
	public String emailCoach(Model model, HttpServletRequest request) {
		
		User realUser = verifyUser(request.getSession());
		
		if(realUser== null){
			return "login";
		}
		
		String message = request.getParameter("message");
		message = realUser.getFirstName() + " " + realUser.getLastName() +" sent you a message through the VASA field scheduling site."+
				"<br /><br />MESSAGE="+message; 
				
		String username = request.getParameter("username");
		User user = userService.findByUserName(username);
		
		String emailAddress = user.getEmailAddress();
		try{
			es.sendEmail(emailAddress, realUser.getEmailAddress(), "New Message", message);
			model.addAttribute("loginerror", "Your Email has been sent.");
		}catch(Exception e){
			model.addAttribute("loginerror", e.getCause() +": "+e.getMessage());
		}
		
		Log l = new Log();
		l.setDescription(realUser.getFirstName() + " " + realUser.getLastName() + " emailed "+user.getLastName());
		l.setCreationDate(new Date());
		scheduleService.save(l);
		
		List<Team> teams = service.findActive();
	    model.addAttribute("teams", teams);
	    
	    model.addAttribute("coaches", userService.findAllCoaches());
		model.addAttribute("sports", service.findAllSports());
		model.addAttribute("seasons", scheduleService.findActiveSeasons());
		model.addAttribute("agegroups", service.findAllAgegroups());
		
		return "team/list";
	}
	
	@RequestMapping(value="/postmessage", method = RequestMethod.GET)
	public String postAddMessage(Model model, HttpServletRequest request) {
		return "user/postmessage";
	}
	
	@RequestMapping(value="/postmessage", method = RequestMethod.POST)
	public String postMessage(Model model, HttpServletRequest request) {
		
		User realUser = verifyUser(request.getSession());
		
		if(realUser== null){
			return "login";
		}
		
		String message = request.getParameter("message");
		String submit = request.getParameter("submit");

		List<Team> teams = service.findActive();
		
		if(submit.equals("Post Message")){
			userService.setGlobalMessage(message);
		}else if(submit.equals("Delete Message")){
			userService.setGlobalMessage(null);
//		}else if(submit.equals("Send Email")){
//			message = realUser.getFirstName() + " " + realUser.getLastName() +" sent you a message through the VASA field scheduling site.<br /><br />MESSAGE="+message;
//			for(Team t : teams){
//				if(!t.getCoach().getStatus().equals(Status.ACTIVE)){
//					continue;
//				}
//				String emailAddress = t.getCoach().getEmailAddress();
//				try{
//					es.sendEmail(emailAddress, realUser.getEmailAddress(), "Global Alert", message);
//				}catch(Exception e){
//				}
//			}
		}else if(submit.equals("Email Baseball")){
			message = realUser.getFirstName() + " " + realUser.getLastName() +" sent you a message through the VASA field scheduling site.<br /><br />MESSAGE="+message;
			for(Team t : teams){
				if(!t.getCoach().getStatus().equals(Status.ACTIVE)){
					continue;
				}
				else if(t.getCoach().getTeam() == null || t.getCoach().getTeam().getSport() == null || !t.getCoach().getTeam().getSport().getName().equals("Baseball")){
					continue;
				}
				String emailAddress = t.getCoach().getEmailAddress();
				try{
					es.sendEmail(emailAddress, realUser.getEmailAddress(), "Global Alert", message);
				}catch(Exception e){
				}
			}
		}else if(submit.equals("Email Softball")){
			message = realUser.getFirstName() + " " + realUser.getLastName() +" sent you a message through the VASA field scheduling site.<br /><br />MESSAGE="+message;
			for(Team t : teams){
				if(!t.getCoach().getStatus().equals(Status.ACTIVE)){
					continue;
				}
				else if(t.getCoach().getTeam() == null || t.getCoach().getTeam().getSport() == null || !t.getCoach().getTeam().getSport().getName().equals("Softball")){
					continue;
				}
				String emailAddress = t.getCoach().getEmailAddress();
				try{
					es.sendEmail(emailAddress, realUser.getEmailAddress(), "Global Alert", message);
				}catch(Exception e){
				}
			}
		}else if(submit.equals("Text Baseball")){
			for(Team t : teams){
				User u = t.getCoach();
				if(!u.getStatus().equals(Status.ACTIVE) || u.getCarrier()==null){
					continue;
				}else if(t.getCoach().getTeam() == null || t.getCoach().getTeam().getSport() == null || !t.getCoach().getTeam().getSport().getName().equals("Baseball")){
					continue;
				}
				String phone = u.getPhone();
				phone = phone.replaceAll("-", "");
				phone = phone.replaceAll(".", "");
				phone = phone.replaceAll(" ", "");
				
				String emailAddress = EmailService.convertToEmail(phone, u.getCarrier());
				
				try{
					es.sendEmail(emailAddress, realUser.getEmailAddress(), "Global Alert", message);
				}catch(Exception e){
				}
			}
		}else if(submit.equals("Text Softball")){
			for(Team t : teams){
				User u = t.getCoach();
				if(!u.getStatus().equals(Status.ACTIVE) || u.getCarrier()==null){
					continue;
				}else if(t.getCoach().getTeam() == null || t.getCoach().getTeam().getSport() == null || !t.getCoach().getTeam().getSport().getName().equals("Softball")){
					continue;
				}
				String phone = u.getPhone();
				phone = phone.replaceAll("-", "");
				phone = phone.replaceAll(".", "");
				phone = phone.replaceAll(" ", "");
				
				String emailAddress = EmailService.convertToEmail(phone, u.getCarrier());
				
				try{
					es.sendEmail(emailAddress, realUser.getEmailAddress(), "Global Alert", message);
				}catch(Exception e){
				}
			}
		}
		
		List<Season> season = seasonService.findAll();
	    model.addAttribute("seasons", season);
		model.addAttribute("user", realUser);
		model.addAttribute("message", userService.getGlobalMessage());
		return "user/home";
	}
	
}
