package com.vasa.scheduling.interfaces.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.vasa.scheduling.domain.Game;
import com.vasa.scheduling.domain.Log;
import com.vasa.scheduling.domain.User;
import com.vasa.scheduling.services.FieldService;
import com.vasa.scheduling.services.ScheduleService;
import com.vasa.scheduling.services.TeamService;

@RequestMapping("/game/modify")
@Controller
public class GameModifyController extends DefaultHandlerController{
	
	@Autowired
	private FieldService service;
	
	@Autowired
	private TeamService teamService;
	
	@Autowired
	private ScheduleService scheduleService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String modify(@RequestParam(required=true, value="game") Integer gameId,
			Model model, HttpServletRequest request) {
		
		User user = verifyUser(request.getSession());
		model.addAttribute("user", user);
		
		if(user== null){
			return "login";
		}
		
		Game f = scheduleService.findGameById(gameId);
		
		String date = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH).format(f.getDate());
		String hour = new SimpleDateFormat("HH", Locale.ENGLISH).format(f.getDate());
		String minute = new SimpleDateFormat("mm", Locale.ENGLISH).format(f.getDate());
		
		model.addAttribute("date", date);
		model.addAttribute("hour", hour);
		model.addAttribute("minute", minute);
		model.addAttribute("fields", service.findAll());
		model.addAttribute("agegroups", teamService.findAllAgegroups());
		model.addAttribute("modifygame", f);
		return "game/modify";
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
		
		Game schedule = null;		
		if(request.getParameter("id") != null){
			schedule = scheduleService.findGameById(Integer.valueOf(request.getParameter("id")));
		}
		
		if(request.getParameter("delete") != null){
			scheduleService.delete(schedule);
			String fieldName = request.getParameter("field");
			Log l = new Log();
			l.setDescription(realUser.getFirstName() + " " + realUser.getLastName() + " deleted a game on "+fieldName + " for " + d);
			l.setCreationDate(new Date());
			scheduleService.save(l);
		}else{
		
			schedule.setCreationDate(new Date());
			String fieldName = request.getParameter("field");
			schedule.setField(scheduleService.findFieldByName(fieldName));
			schedule.setAgeGroup(teamService.findAgeGroupById(Integer.valueOf(request.getParameter("ageGroup"))));
			schedule.setDate(date.getTime());
			schedule.setDuration(request.getParameter("duration"));
			schedule.setHomeTeam(request.getParameter("homeTeam"));
			schedule.setAwayTeam(request.getParameter("awayTeam"));
			scheduleService.save(schedule);
		
			Log l = new Log();
			l.setDescription(realUser.getFirstName() + " " + realUser.getLastName() + " modified a game on "+fieldName + " for " + d);
			l.setCreationDate(new Date());
			scheduleService.save(l);
		}
				
		return "user/home";
	}
	
}
