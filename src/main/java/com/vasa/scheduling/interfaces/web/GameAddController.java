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
import com.vasa.scheduling.domain.User;
import com.vasa.scheduling.services.ScheduleService;

@RequestMapping("/game/add")
@Controller
public class GameAddController extends DefaultHandlerController{
	
	@Autowired
	private ScheduleService service;
	
	@RequestMapping(method = RequestMethod.GET)
	public String modify(Model model, HttpServletRequest request) {
		
		User user = verifyUser(request.getSession());
		model.addAttribute("user", user);
		
		if(user== null){
			return "login";
		}
		
		model.addAttribute("fields", service.findAllFields());
		
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
		
		String duration = request.getParameter("duration");
		int multiple = 0;
		
		if(duration.indexOf("h")>0){
			String hours = duration.substring(0, duration.indexOf("h")).trim();
			multiple = Integer.valueOf(hours) * 2;
		}
		if(duration.indexOf("m")>0){
			multiple += 1;
		}
		
		if(multiple>0){
			date.add(Calendar.MINUTE, -30);
			for(int x=0; x<multiple; x++){
				FieldSchedule schedule = new FieldSchedule();
				schedule.setCreationDate(new Date());
				schedule.setField(service.findFieldByName(request.getParameter("field")));
				date.add(Calendar.MINUTE, 30);
				schedule.setDate(date.getTime());
				schedule.setGame(true);
				schedule.setGameDescription(request.getParameter("description"));
				service.save(schedule);
			}
		}else{
			FieldSchedule schedule = new FieldSchedule();
			schedule.setCreationDate(new Date());
			schedule.setDate(date.getTime());
			schedule.setGame(true);
			schedule.setGameDescription(request.getParameter("description"));
			schedule.setField(service.findFieldByName(request.getParameter("field")));
			service.save(schedule);
		}
		
		
		return "user/home";
	}
	
}
