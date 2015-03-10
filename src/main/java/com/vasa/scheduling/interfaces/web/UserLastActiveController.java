package com.vasa.scheduling.interfaces.web;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.vasa.scheduling.domain.FieldSchedule;
import com.vasa.scheduling.domain.User;
import com.vasa.scheduling.services.ScheduleService;
import com.vasa.scheduling.services.UserService;

/**
 * Handles requests for the application home page.
 */
@RequestMapping("/user/lastactive")
@Controller
public class UserLastActiveController extends DefaultHandlerController{
	
	@Autowired private UserService service;
	@Autowired private ScheduleService schedService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model, HttpServletRequest request) {
		
		User user = verifyUser(request.getSession());
		model.addAttribute("user", user);
		
		if(user== null){
			return "login";
		}
		
		User lastActive = service.getLastActive();
		FieldSchedule lastScheduled = schedService.getLastActive();
		
		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy hh:mm:ss a");
		
		if(lastActive.getLastLogin().after(lastScheduled.getCreationDate())){
			model.addAttribute("active_user", lastActive.getFirstName() + " " +lastActive.getLastName());
			
			Calendar c = Calendar.getInstance();
			c.setTime(lastActive.getLastLogin());
			c.add(Calendar.HOUR_OF_DAY, -1);
			
			model.addAttribute("last_active", sdf.format(c.getTime()));
		}else{
			User u = lastScheduled.getTeam().getCoach();
			model.addAttribute("active_user", u.getFirstName() + " " +u.getLastName());
			
			Calendar c = Calendar.getInstance();
			c.setTime(lastScheduled.getCreationDate());
			c.add(Calendar.HOUR_OF_DAY, -1);
			
			model.addAttribute("last_active", sdf.format(c.getTime()));
		}

	    return "user/lastactive";
	}
	
}
