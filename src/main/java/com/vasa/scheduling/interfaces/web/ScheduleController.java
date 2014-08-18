package com.vasa.scheduling.interfaces.web;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.vasa.scheduling.domain.FieldSchedule;
import com.vasa.scheduling.domain.User;
import com.vasa.scheduling.services.ScheduleService;

@Controller
@RequestMapping("/schedule/list")
public class ScheduleController extends DefaultHandlerController {

	@Autowired
	private ScheduleService service;
	
	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model, HttpServletRequest request) {
		
		User user = verifyUser(request.getSession());
		model.addAttribute("user", user);
		
		if(user== null){
			return "login";
		}
		
		Date startDate = new Date();
		Calendar sunday = Calendar.getInstance();
		sunday.setTime(startDate);
		sunday.set(Calendar.MINUTE, 0);
		sunday.set(Calendar.HOUR, 0);
		sunday.set(Calendar.DAY_OF_WEEK, 1);
		
		Date startOfWeek = sunday.getTime();
		
		ArrayList<String> fields = new ArrayList<String>();
		fields.add("FM North");
		fields.add("FM South");
		
		// schedule contains the entire schedule for every field
		HashMap<String, HashMap<String,ArrayList<String>>> schedule = new HashMap<String, HashMap<String,ArrayList<String>>>();
		
		for(String field: fields){
			sunday.setTime(startDate);
			sunday.set(Calendar.MINUTE, 0);
			sunday.set(Calendar.HOUR, 0);
			sunday.set(Calendar.DAY_OF_WEEK, 1);
			// week - key is the day, value is the fields
			HashMap<String,ArrayList<String>> week = new HashMap<String, ArrayList<String>>();
			week.put("Sunday", getFieldDay(sunday.getTime(), field));
			sunday.add(Calendar.DAY_OF_YEAR, 1);
			week.put("Monday", getFieldDay(sunday.getTime(), field));
			sunday.add(Calendar.DAY_OF_YEAR, 1);
			week.put("Tuesday", getFieldDay(sunday.getTime(), field));
			sunday.add(Calendar.DAY_OF_YEAR, 1);
			week.put("Wednesday", getFieldDay(sunday.getTime(), field));
			sunday.add(Calendar.DAY_OF_YEAR, 1);
			week.put("Thursday", getFieldDay(sunday.getTime(), field));
			sunday.add(Calendar.DAY_OF_YEAR, 1);
			week.put("Friday", getFieldDay(sunday.getTime(), field));
			sunday.add(Calendar.DAY_OF_YEAR, 1);
			week.put("Saturday", getFieldDay(sunday.getTime(), field));
			schedule.put(field, week);
		}
		
		model.addAttribute("sunday", startOfWeek);
		model.addAttribute("fields", fields);
		model.addAttribute("schedule",schedule);
		
	    return "schedule/list";
	}
	
	@RequestMapping(value="/add", method = RequestMethod.GET)
	public String add(@RequestParam(required=true, value="date") String date,
			@RequestParam(required=true, value="field") String field,
			Model model, 
			HttpServletRequest request) {
		 return list(model, request);
	}
	
	@RequestMapping(value="/delete", method = RequestMethod.GET)
	public String delete(@RequestParam(required=true, value="date") String date,
			@RequestParam(required=true, value="field") String field,
			Model model, 
			HttpServletRequest request) {
		 return list(model, request);
	}

	private ArrayList<String> getFieldDay(Date date, String field) {
		
		List<FieldSchedule> schedules = service.findByDateField(date, field);
		ArrayList<String> day = new ArrayList<String>();
		
		day.add(null); // 9:00
		day.add(null); // 9:30
		day.add(null); // 10:00
		day.add(null); // 10:30
		day.add(null); // 11:00
		day.add(null); // 11:30
		day.add(null); // 12:00
		day.add(null); // 12:30
		day.add(null); // 1:00
		day.add(null); // 1:30
		day.add(null); // 2:00
		day.add(null); // 2:30
		day.add(null); // 3:00
		day.add(null); // 3:30
		day.add(null); // 4:00
		day.add(null); // 4:30
		day.add(null); // 5:00
		day.add(null); // 5:30
		day.add(null); // 6:00
		day.add(null); // 6:30
		day.add(null); // 7:00
		day.add(null); // 7:30
		day.add(null); // 8:00
		day.add(null); // 8:30
		day.add(null); // 9:00
		day.add(null); // 9:30

		for(FieldSchedule schedule: schedules){
			int slotNumber = getHourSlotNumber(schedule.getDate());
			day.set(slotNumber, schedule.getTeam().getName()+" - "+
					schedule.getTeam().getCoach().getLastName()+" - "+
					schedule.getTeam().getAgeGroup().getName());
		}
		return day;
	}
	
	private int getHourSlotNumber(Date d){
		
		Calendar timeSlot = Calendar.getInstance();
		timeSlot.setTime(d);
		timeSlot.set(Calendar.MINUTE, 0);
		timeSlot.set(Calendar.HOUR_OF_DAY, 9);
		
		int x = 0;
		
		while(timeSlot.getTime().before(d)){
			x++;
			timeSlot.add(Calendar.MINUTE, 30);
		}
		
		return x;
	}
}