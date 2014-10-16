package com.vasa.scheduling.interfaces.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.vasa.scheduling.domain.FieldSchedule;
import com.vasa.scheduling.domain.Game;
import com.vasa.scheduling.domain.User;
import com.vasa.scheduling.services.ScheduleService;
import com.vasa.scheduling.services.TeamService;

@RequestMapping("/game/add")
@Controller
public class GameAddController extends DefaultHandlerController{

	@Autowired
	private ScheduleService service;
	
	@Autowired
	private TeamService teamService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String add(Model model, HttpServletRequest request) {
		
		User user = verifyUser(request.getSession());
		model.addAttribute("user", user);
		
		if(user== null){
			return "login";
		}
		
		model.addAttribute("fields", service.findAllFields());
		model.addAttribute("agegroups", teamService.findAllAgegroups());
		
		return "game/add";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String save(Model model, HttpServletRequest request) {
		
		
		User realUser = verifyUser(request.getSession());
		
		if(realUser== null){
			return "login";
		}
		
		String dateString = request.getParameter("date");
		
		Date d = null;
		try {
			d = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH).parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		Calendar date = Calendar.getInstance();
		date.setTime(d);
		date.set(Calendar.HOUR_OF_DAY, Integer.valueOf(request.getParameter("hour")));
		date.set(Calendar.MINUTE, Integer.valueOf(request.getParameter("minute")));
		
		Game schedule = new Game();
		schedule.setCreationDate(new Date());
		schedule.setField(service.findFieldByName(request.getParameter("field")));
		schedule.setAgeGroup(teamService.findAgeGroupById(Integer.valueOf(request.getParameter("ageGroup"))));
		schedule.setDate(date.getTime());
		schedule.setDuration(request.getParameter("duration"));
		schedule.setHomeTeam(request.getParameter("homeTeam"));
		schedule.setAwayTeam(request.getParameter("awayTeam"));
		service.save(schedule);
				
		return "user/home";
	}
	
}
