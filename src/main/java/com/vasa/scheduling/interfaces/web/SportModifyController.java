package com.vasa.scheduling.interfaces.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.vasa.scheduling.domain.Sport;
import com.vasa.scheduling.domain.Team;
import com.vasa.scheduling.domain.User;
import com.vasa.scheduling.enums.Classification;
import com.vasa.scheduling.enums.DayOfWeek;
import com.vasa.scheduling.services.ScheduleService;
import com.vasa.scheduling.services.SportService;
import com.vasa.scheduling.services.TeamService;
import com.vasa.scheduling.services.UserService;

@RequestMapping("/sport/modify")
@Controller
public class SportModifyController extends DefaultHandlerController{
	
	@Autowired
	private SportService service;
	
	@RequestMapping(method = RequestMethod.GET)
	public String modify(@RequestParam(required=true, value="sport") Integer sportId,
			Model model, HttpServletRequest request) {
		
		User user = verifyUser(request.getSession());
		model.addAttribute("user", user);
		
		if(user== null){
			return "login";
		}
		
		model.addAttribute("modifysport", service.findById(sportId));
		model.addAttribute("days", DayOfWeek.getDisplayNames());
		
		return "sport/modify";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String save(Model model, HttpServletRequest request) {
		
		
		User realUser = verifyUser(request.getSession());
		
		if(realUser== null){
			return "login";
		}
		
		Sport sport = null;
		
		
		if(request.getParameter("id") != null){
			sport = service.findById(Integer.valueOf(request.getParameter("id")));
		}
		
		if(request.getParameter("delete") != null){
			service.delete(sport);
		}else{
			sport.setName(request.getParameter("name"));
			
			String value = request.getParameter("time");
			if(value != null){
				sport.setTime(Integer.valueOf(value));
			}else{
				sport.setTime(null);
			}
			
			value = request.getParameter("dayOfWeek");
			if(value != null){
				sport.setDayOfWeek(DayOfWeek.toEnumFromCode(Integer.valueOf(value)));
			}else{
				sport.setDayOfWeek(null);
			}
			
			value = request.getParameter("nonVasaDayOfWeek");
			if(value != null){
				sport.setNonVasaDayOfWeek(DayOfWeek.toEnumFromCode(Integer.valueOf(value)));
			}else{
				sport.setNonVasaDayOfWeek(null);
			}
			
			service.save(sport);
		}
		
		List<Sport> sports = service.findAll();	
	    model.addAttribute("sports", sports);
		return "sport/list";
	}
	
}
