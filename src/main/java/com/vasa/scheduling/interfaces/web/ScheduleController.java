package com.vasa.scheduling.interfaces.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.vasa.scheduling.domain.Fields;
import com.vasa.scheduling.domain.Season;
import com.vasa.scheduling.domain.Sport;
import com.vasa.scheduling.domain.Team;
import com.vasa.scheduling.domain.User;
import com.vasa.scheduling.services.ScheduleService;

@Controller
@RequestMapping("/schedule/calendar")
public class ScheduleController extends DefaultHandlerController {

	// TODO: Only get Active Fields
	// TODO: Add Error Handleing
	
	@Autowired
	private ScheduleService service;
	
	@RequestMapping(method = RequestMethod.GET)
	public String list(@RequestParam(required=false, value="date") String date, Model model, HttpServletRequest request) {
		
		User user = verifyUser(request.getSession());
		model.addAttribute("user", user);
		
		if(user== null){
			return "login";
		}
		
		Calendar sunday = Calendar.getInstance();
		Date startDate = new Date();
		
		if(date != null){
			try {
				SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
				startDate = formatter.parse(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		sunday.setTime(startDate);
		sunday.set(Calendar.MINUTE, 0);
		sunday.set(Calendar.HOUR, 0);
		sunday.set(Calendar.DAY_OF_WEEK, 1);
		
		Date startOfWeek = sunday.getTime();
		
		Calendar today = Calendar.getInstance();
		Date d = new Date();
		today.setTime(d);
		today.set(Calendar.MINUTE, 0);
		today.set(Calendar.HOUR, 0);
		today.add(Calendar.DAY_OF_YEAR, 7);
		
		boolean locked = false;
		if(startOfWeek.after(today.getTime())){
			locked = true;
		}
		
		List<Fields> fields = null;
		if(user.getTeam() != null){
			Sport sport = user.getTeam().getSport();
			fields = service.findAllFields(sport);
			if(sport.getName().equals("Baseball")){
				sport = service.findSportByName("Softball");
				if(sport != null){
					fields.addAll(service.findAllFields(sport));
				}
			}else if(sport.getName().equals("Softball")){
				sport = service.findSportByName("Baseball");
				if(sport != null){
					fields.addAll(service.findAllFields(sport));
				}
			}
		}else{
			// Get the fields for the sport that is active
			List<Season> activeSeason = service.findActiveSeasons();
			if(activeSeason.size()>0){
				Season season = activeSeason.get(0);
				fields = service.findAllFields(season.getSport());
				if(season.getSport().getName().equals("Baseball")){
					Sport sport = service.findSportByName("Softball");
					if(sport != null){
						fields.addAll(service.findAllFields(sport));
					}
				}else if(season.getSport().getName().equals("Softball")){
					Sport sport = service.findSportByName("Baseball");
					if(sport != null){
						fields.addAll(service.findAllFields(sport));
					}
				}
			}else{
				fields = service.findAllFields();
			}
		}
		
		// schedule contains the entire schedule for every field
		HashMap<String, HashMap<String,ArrayList<String>>> schedule = new HashMap<String, HashMap<String,ArrayList<String>>>();
		
		for(Fields field: fields){
			sunday.setTime(startDate);
			sunday.set(Calendar.MINUTE, 0);
			sunday.set(Calendar.HOUR, 0);
			sunday.set(Calendar.DAY_OF_WEEK, 1);
			// week - key is the day, value is the fields
			HashMap<String,ArrayList<String>> week = new HashMap<String, ArrayList<String>>();
			week.put("Sunday", getFieldDay(sunday.getTime(), field.getName()));
			sunday.add(Calendar.DAY_OF_YEAR, 1);
			week.put("Monday", getFieldDay(sunday.getTime(), field.getName()));
			sunday.add(Calendar.DAY_OF_YEAR, 1);
			week.put("Tuesday", getFieldDay(sunday.getTime(), field.getName()));
			sunday.add(Calendar.DAY_OF_YEAR, 1);
			week.put("Wednesday", getFieldDay(sunday.getTime(), field.getName()));
			sunday.add(Calendar.DAY_OF_YEAR, 1);
			week.put("Thursday", getFieldDay(sunday.getTime(), field.getName()));
			sunday.add(Calendar.DAY_OF_YEAR, 1);
			week.put("Friday", getFieldDay(sunday.getTime(), field.getName()));
			sunday.add(Calendar.DAY_OF_YEAR, 1);
			week.put("Saturday", getFieldDay(sunday.getTime(), field.getName()));
			schedule.put(field.getName(), week);
		}
		
		model.addAttribute("sunday", startOfWeek);
		model.addAttribute("fields", fields);
		model.addAttribute("schedule",schedule);
		model.addAttribute("locked", locked);
		
	    return "schedule/calendar";
	}
	
	@RequestMapping(value="/add", method = RequestMethod.GET)
	public String add(@RequestParam(required=true, value="date") String date,
			@RequestParam(required=true, value="field") String field,
			@RequestParam(required=false, value="date") String userId,
			Model model, 
			HttpServletRequest request) {
		
		User user = verifyUser(request.getSession());
		
		if(user== null){
			return "login";
		}
		
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm");
			Date calendarDay = formatter.parse(date);
		
			FieldSchedule schedule = service.findByDateField(calendarDay, field);
			
			if(schedule == null && (user.getTeam().getPracticeLimit() == null || validateRequest(model, user.getTeam(), calendarDay))){
				schedule = new FieldSchedule();
				schedule.setCreationDate(new Date());
				schedule.setDate(calendarDay);
				schedule.setField(service.findFieldByName(field));
				schedule.setTeam(user.getTeam());
				service.save(schedule);
			}
			
		} catch (ParseException e) {
			model.addAttribute("error", e.getMessage());
			e.printStackTrace();
		}
		
		return list(date, model, request);
	}
	
	private boolean validateRequest(Model model, Team team, Date calendarDay) {
		List<FieldSchedule> schedules = service.findScheduleForWeek(team, calendarDay);
		if(schedules.size() > team.getPracticeLimit()){
			model.addAttribute("error", "You are limited to "+team.getPracticeLimit()+" hour(s) per week.");
			return false;
		}
		return true;
	}

	@RequestMapping(value="/delete", method = RequestMethod.GET)
	public String delete(@RequestParam(required=true, value="date") String date,
			@RequestParam(required=true, value="field") String field,
			Model model, 
			HttpServletRequest request) {
		
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm");
			Date calenderDay = formatter.parse(date);
		
			FieldSchedule schedule = service.findByDateField(calenderDay, field);
			User user = verifyUser(request.getSession());
			
			if(schedule != null && schedule.getTeam().getId().equals(user.getTeam().getId())){
				service.delete(schedule);
			}
			
		} catch (ParseException e) {
			model.addAttribute("error", e.getMessage());
			e.printStackTrace();
		}
		
		return list(date, model, request);
	}
	
	private ArrayList<String> getFieldDay(Date date, String field) {
		
		List<FieldSchedule> schedules = service.findByDayField(date, field);
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
			if(schedule.getGame()){
				day.set(slotNumber, "RESERVED FOR GAMES");
			}else{
				day.set(slotNumber, schedule.getTeam().getName()+" - "+
					schedule.getTeam().getCoach().getLastName()+" - "+
					schedule.getTeam().getAgeGroup().getName());
			}
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