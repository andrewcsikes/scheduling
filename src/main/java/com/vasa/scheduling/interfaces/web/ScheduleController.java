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
import com.vasa.scheduling.domain.Game;
import com.vasa.scheduling.domain.Log;
import com.vasa.scheduling.domain.Season;
import com.vasa.scheduling.domain.Sport;
import com.vasa.scheduling.domain.Team;
import com.vasa.scheduling.domain.User;
import com.vasa.scheduling.enums.Status;
import com.vasa.scheduling.enums.UserType;
import com.vasa.scheduling.services.EmailService;
import com.vasa.scheduling.services.LockingService;
import com.vasa.scheduling.services.ScheduleService;
import com.vasa.scheduling.services.TeamService;
import com.vasa.scheduling.services.DailyMessageService;

@Controller
@RequestMapping("/schedule/calendar")
public class ScheduleController extends DefaultHandlerController {

	@Autowired
	private EmailService es;
	
	@Autowired
	private ScheduleService service;
	
	@Autowired
	private TeamService teamService;
	
	@Autowired
	private LockingService lockingService;
	
	@Autowired
	private DailyMessageService weatherService;
	
	@RequestMapping(value="/quick", method = RequestMethod.GET)
	public String quick(@RequestParam(required=false, value="date") String date, Model model, HttpServletRequest request) {
		buildCalendar(date, model, null);
		return "schedule/quick-calendar";
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String list(@RequestParam(required=false, value="date") String date, Model model, HttpServletRequest request) {
		
		User user = verifyUser(request.getSession());
		model.addAttribute("user", user);
		
		if(user== null){
			return "login";
		}
		
		buildCalendar(date, model, user);
		
	    return "schedule/calendar";
	}

	protected void buildCalendar(String date, Model model, User user) {
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
		
		List<Fields> fields = getFields(user);
		
		// schedule contains the entire schedule for every field
		HashMap<String, HashMap<String,ArrayList<String>>> schedule = new HashMap<String, HashMap<String,ArrayList<String>>>();
		
		boolean anylocked = false;
		for(Fields field: fields){
			
			boolean locked = false;
			if(user==null){
				locked = true;
			}
			else if(user.getUserType().equals(UserType.ADMIN) || 
					user.getUserType().equals(UserType.COMMISSIONER)){
				
				if(user.getUserType().equals(UserType.ADMIN)){
					locked=false;
				}else{
					locked = lockingService.getLocked(user.getTeam(), field, startOfWeek);
				}
				
				if(user.getTeam() != null && user.getTeam().getSport() != null){
					Sport s = user.getTeam().getSport();
					if(s.getName().equals("Baseball") || s.getName().equals("Softball")){
						s = service.findSportByName("Baseball");
						List<Team> teams = teamService.findTeamsBySport(s);
						s = service.findSportByName("Softball");
						teams.addAll(teamService.findTeamsBySport(s));
						model.addAttribute("teams", teams);
					}else{
						model.addAttribute("teams", teamService.findTeamsBySport(s));
					}
				}else{
					model.addAttribute("teams", teamService.findActive());
				}
			}else{
				locked = lockingService.getLocked(user.getTeam(), field, startOfWeek);
			}
			anylocked=anylocked||locked;
			model.addAttribute(field.getName()+"locked",locked);
			
			sunday.setTime(startDate);
			sunday.set(Calendar.MINUTE, 0);
			sunday.set(Calendar.HOUR, 0);
			sunday.set(Calendar.DAY_OF_WEEK, 1);
			// week - key is the day, value is the fields
			HashMap<String,ArrayList<String>> week = new HashMap<String, ArrayList<String>>();
			Team team = user==null ? null : user.getTeam();
			week.put("Sunday", getFieldDay(sunday.getTime(), field, team));
			sunday.add(Calendar.DAY_OF_YEAR, 1);
			week.put("Monday", getFieldDay(sunday.getTime(), field, team));
			sunday.add(Calendar.DAY_OF_YEAR, 1);
			week.put("Tuesday", getFieldDay(sunday.getTime(), field, team));
			sunday.add(Calendar.DAY_OF_YEAR, 1);
			week.put("Wednesday", getFieldDay(sunday.getTime(), field, team));
			sunday.add(Calendar.DAY_OF_YEAR, 1);
			week.put("Thursday", getFieldDay(sunday.getTime(), field, team));
			sunday.add(Calendar.DAY_OF_YEAR, 1);
			week.put("Friday", getFieldDay(sunday.getTime(), field, team));
			sunday.add(Calendar.DAY_OF_YEAR, 1);
			week.put("Saturday", getFieldDay(sunday.getTime(), field, team));
			schedule.put(field.getName(), week);
		}
		
		model.addAttribute("sunday", startOfWeek);
		model.addAttribute("fields", fields);
		model.addAttribute("schedule",schedule);
		model.addAttribute("locked", anylocked);
		
		sunday.set(Calendar.DAY_OF_WEEK,1);
		
		if(sunday.compareTo(Calendar.getInstance())<5){
			weatherService.addDailyMessages(model, sunday);
		}
	}

	protected List<Fields> getFields(User user) {

		List<Fields> fields = null;
		if(user != null && user.getTeam() != null && user.getTeam().getSport() != null && user.getTeam().getSport().getName().equals("Baseball")){
			Sport sport = teamService.findSportById(2);
			fields=service.findAllFields(sport);
		
			sport = teamService.findSportById(1);
			fields.addAll(service.findAllFields(sport));
		}else{
			Sport sport = teamService.findSportById(1);
			fields=service.findAllFields(sport);
		
			sport = teamService.findSportById(2);
			fields.addAll(service.findAllFields(sport));
		}
		
		return fields;
	}
	
	@RequestMapping(value="/add", method = RequestMethod.POST)
	public String add(@RequestParam(required=false, value="field") String field,
			@RequestParam(required=false, value="team") String teamId,
			@RequestParam(required=false, value="date") String date,
			@RequestParam(required=false, value="hour") String hour,
			@RequestParam(required=false, value="minute") String minute,
			@RequestParam(required=false, value="duration") String duration,
			Model model, 
			HttpServletRequest request) {
		
		String listDate = null;
		User user = verifyUser(request.getSession());
		
		if(user== null){
			return "login";
		}
		
		try{
			
			int multiple = 0;
			if(duration.indexOf("h")>0){
				String hours = duration.substring(0, duration.indexOf("h")).trim();
				multiple = Integer.valueOf(hours) * 2;
			}
			if(duration.indexOf("m")>0){
				multiple += 1;
			}
			
			SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm");
			Date calendarDay = formatter.parse(date + " " + hour + ":" + minute);
			Calendar times = Calendar.getInstance();
			times.setTime(calendarDay);
			
			for(int x=0; x<multiple; x++){
				FieldSchedule schedule = service.findByDateField(times.getTime(), field);
				if(schedule == null){
					listDate = add(user, formatter.format(times.getTime()), field, teamId, model);
				}else{
					Team t = schedule.getTeam();
					model.addAttribute("error", "Time "+formatter.format(times.getTime())+" is already taken by "+t.getAgeGroup().getName()+"-"+t.getName()+"-"+t.getCoach().getLastName()+".");
					Calendar sunday = Calendar.getInstance();
					sunday.setTime(times.getTime());
					sunday.set(Calendar.MINUTE, 0);
					sunday.set(Calendar.HOUR, 0);
					sunday.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
					listDate = formatter.format(sunday.getTime());
					break;
				}
				times.add(Calendar.MINUTE, 30);
			}
			
			Log l = new Log();
			Team t = teamService.findById(Integer.valueOf(teamId));
			l.setDescription(user.getFirstName() + " " + user.getLastName() + " added a practice on "+field + " for Team: "+
					t.getAgeGroup().getName() + " - " + t.getName() + " - " + t.getCoach().getLastName() + 
					" on " + date +" " +hour+ ":"+minute);
			l.setCreationDate(new Date());
			service.save(l);
			
		}catch(ParseException e){
			System.out.println("Parse Error: " +e.getMessage());
		}
		
		return list(listDate, model, request);
	}

	@RequestMapping(value="/add", method = RequestMethod.GET)
	public String add(@RequestParam(required=true, value="date") String date,
			@RequestParam(required=true, value="field") String field,
			@RequestParam(required=false, value="team") String teamId,
			Model model, 
			HttpServletRequest request) {
		
		User user = verifyUser(request.getSession());
		
		if(user== null){
			return "login";
		}
		
		String listDate = add(user, date, field, teamId, model);
		
		return list(listDate, model, request);
	}
	
	private String add(User user, String date, String field, String teamId, Model model){
		String listDate = null; 
		
		Team t = null;
		if(teamId != null){
			t=teamService.findById(Integer.valueOf(teamId));
		}
		
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm");
			Date calendarDay = formatter.parse(date);
		
			FieldSchedule schedule = service.findByDateField(calendarDay, field);
			
			if(schedule == null && validateRequest(model, field, user.getTeam(), calendarDay)){
				schedule = new FieldSchedule();
				schedule.setCreationDate(new Date());
				schedule.setDate(calendarDay);
				schedule.setField(service.findFieldByName(field));
				if(t!=null){
					schedule.setTeam(t);
				}else{
					schedule.setTeam(user.getTeam());
				}
				service.save(schedule);
				
				Log l = new Log();
				l.setDescription(user.getFirstName() + " " + user.getLastName() + " added a practice on "+field + " for " + calendarDay);
				l.setCreationDate(new Date());
				service.save(l);
			}
			
			Calendar sunday = Calendar.getInstance();
			sunday.setTime(calendarDay);
			sunday.set(Calendar.MINUTE, 0);
			sunday.set(Calendar.HOUR, 0);
			sunday.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
			
			listDate = formatter.format(sunday.getTime());
			
		} catch (ParseException e) {
			model.addAttribute("error", e.getMessage());
			e.printStackTrace(); 
		}
		return listDate;
	}
	
	protected boolean validateRequest(Model model, String field, Team team, Date calendarDay) {
		
		Calendar today = Calendar.getInstance();
		Calendar week = Calendar.getInstance();
		week.setTime(calendarDay);
		week.set(Calendar.DAY_OF_WEEK,1);
		
		// This week
		if(today.compareTo(week)>=0){
			return true;
		}
		
		// The week before
		else if(today.compareTo(week)<0){
			if(today.get(Calendar.DAY_OF_WEEK)>Calendar.FRIDAY){
				return true;
			}
		}
		
		if(field.startsWith("Batting Cage")){
			// ignore times
			return true;
		}
		
		boolean validate = validateWeeklyPracticeLimit(model, team, calendarDay);
		if(validate){
			validate = validateDailyPracticeLimit(model, team, calendarDay);
		}
		return validate;
	}

	protected boolean validateWeeklyPracticeLimit(Model model, Team team, Date calendarDay) {
		if(team.getWeeklyPracticeLimit()==null){
			return true;
		}
		List<FieldSchedule> schedules = service.findScheduleForWeek(team, calendarDay);
		int blocks = 0;
		for(FieldSchedule s: schedules){
			if(s.getField().getName().startsWith("Batting Cage")){
				// ignore
			}else{
				blocks ++;
			}
			
		}
		if(blocks >= team.getWeeklyPracticeLimit()*2){
			model.addAttribute("error", "You are limited to "+team.getWeeklyPracticeLimit()+" hour(s) per week.");
			return false;
		}
		return true;
	}
	
	protected boolean validateDailyPracticeLimit(Model model, Team team, Date calendarDay) {
		if(team.getPracticeLimit()==null){
			return true;
		}
		List<FieldSchedule> schedules = service.findScheduleForDay(team, calendarDay);
		int blocks = 0;
		for(FieldSchedule s: schedules){
			if(s.getField().getName().startsWith("Batting Cage")){
				// ignore
			}else{
				blocks ++;
			}
			
		}
		if(blocks >= team.getPracticeLimit()*2){
			model.addAttribute("error", "You are limited to "+team.getPracticeLimit()+" hour(s) per day.");
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
			
			if(schedule != null && 
					schedule.getTeam().getId().equals(user.getTeam().getId())){
				service.delete(schedule);
				
				Log l = new Log();
				l.setDescription(user.getFirstName() + " " + user.getLastName() + " deleted a practice on "+field + " for " + calenderDay);
				l.setCreationDate(new Date());
				service.save(l);
				
				// If date is current week, email league coaches.
				Calendar today = Calendar.getInstance();
				Calendar slot = Calendar.getInstance();
				slot.setTime(schedule.getDate());
				Calendar week = Calendar.getInstance();
				week.setTime(schedule.getDate());
				week.set(Calendar.DAY_OF_WEEK,1);
				if(today.compareTo(week)>0 && today.compareTo(slot)<0){
					try{
						// loop through all the coaches for the sport
						for(Team t : teamService.findTeamsBySport(schedule.getField().getSport())){
							User u = t.getCoach();
							if(u.getStatus() == Status.INACTIVE || u.isSkipNotifications()){
								continue;
							}
							SimpleDateFormat formatter2 = new SimpleDateFormat("EEE, MMM d hh:mm a");
							String message = "The practice spot for "+schedule.getField().getName()+" at "+formatter2.format(schedule.getDate())+" was previously scheduled, but is now available.";
							String emailAddress = u.getEmailAddress();
							if(emailAddress != null){
								es.sendEmail(emailAddress, "Time Slot has been made Available", message);
							}
						}
					}catch(Exception e){
						model.addAttribute("error", e.getCause() +": "+e.getMessage());
					}
				}
			}else if(schedule != null && (user.getUserType().equals(UserType.ADMIN) ||
					user.getUserType().equals(UserType.COMMISSIONER))){
				User coach = schedule.getTeam().getCoach();
				service.delete(schedule);
				
				Log l = new Log();
				l.setDescription(user.getFirstName() + " " + user.getLastName() + " deleted "+coach.getLastName()+"'s practice on "+field + " for " + calenderDay);
				l.setCreationDate(new Date());
				service.save(l);
				
				// email coach, ADMIN deleted his schedule
				try{
					SimpleDateFormat formatter2 = new SimpleDateFormat("EEE, MMM d HH:mm");
					String message = "Your practice for "+schedule.getField().getName()+" at "+formatter2.format(schedule.getDate())+" was removed by "+user.getFirstName()+" "+user.getLastName();
					String emailAddress = schedule.getTeam().getCoach().getEmailAddress();
					if(emailAddress != null){
						es.sendEmail(emailAddress, "Practice removed", message);
					}
				}catch(Exception e){
					model.addAttribute("error", e.getCause() +": "+e.getMessage());
				}
			}
			
		} catch (ParseException e) {
			model.addAttribute("error", e.getMessage());
			e.printStackTrace();
		}
		
		return list(date, model, request);
	}
	
	protected ArrayList<String> getFieldDay(Date date, Fields field, Team team) {
		
		ArrayList<String> day = new ArrayList<String>();
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		
		if(cal.getTime().compareTo(new Date()) < 0){
			addBlockedDay(day);
		} else if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
			if(!field.isAvailableSunday()){
				addBlockedDay(day);
			}else if(field.getSundayStartTime() != null){
				addBlockedTimes(day,field.getSundayStartTime(), field.getSundayEndTime());
			}else{
				addBlankDay(day);
			}
		}else if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY){
			if(!field.isAvailableMonday()){
				addBlockedDay(day);
			}else if(field.getMondayStartTime() != null){
				addBlockedTimes(day,field.getMondayStartTime(), field.getMondayEndTime());
			}else{
				addBlankDay(day);
			}
		}else if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY){
			if(!field.isAvailableTuesday()){
				addBlockedDay(day);
			}else if(field.getTuesdayStartTime() != null){
				addBlockedTimes(day,field.getTuesdayStartTime(), field.getTuesdayEndTime());
			}else{
				addBlankDay(day);
			}
		}else if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY){
			if(!field.isAvailableWednesday()){
				addBlockedDay(day);
			}else if(field.getWednesdayStartTime() != null){
				addBlockedTimes(day,field.getWednesdayStartTime(), field.getWednesdayEndTime());
			}else{
				addBlankDay(day);
			}
		}else if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY){
			if(!field.isAvailableThursday()){
				addBlockedDay(day);
			}else if(field.getThursdayStartTime() != null){
				addBlockedTimes(day,field.getThursdayStartTime(), field.getThursdayEndTime());
			}else{
				addBlankDay(day);
			}
		}else if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY){
			if(!field.isAvailableFriday()){
				addBlockedDay(day);
			}else if(field.getFridayStartTime() != null){
				addBlockedTimes(day,field.getFridayStartTime(), field.getFridayEndTime());
			}else{
				addBlankDay(day);
			}
		}else if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY){
			if(!field.isAvailableSaturday()){
				addBlockedDay(day);
			}else if(field.getSaturdayStartTime() != null){
				addBlockedTimes(day,field.getSaturdayStartTime(), field.getSaturdayEndTime());
			}else{
				addBlankDay(day);
			}
		}
		
		Season season = service.findSeason(field.getSport());
		if(season != null && season.getApplySchedulingRules() &&
				team != null &&
				(team.getCoach().getUserType() != UserType.ADMIN ||
					team.getCoach().getUserType() != UserType.COMMISSIONER)){
			lockingService.setBlockedTimesBasedOnRules(field, team, date, day);
		}
		List<FieldSchedule> schedules = service.findByDayField(date, field.getName());
		List<Game> games = service.findGamesByDayField(date, field.getName());
		
		for(FieldSchedule schedule: schedules){
			int slotNumber = getHourSlotNumber(schedule.getDate());
			
			if(schedule.getTeam()==null)continue;
			
			day.set(slotNumber, schedule.getTeam().getName()+" - "+
				schedule.getTeam().getCoach().getLastName()+" - "+
				schedule.getTeam().getAgeGroup().getName());
		}
		
		for(Game g: games){
			int slotNumber = getHourSlotNumber(g.getDate());
			
			String duration = g.getDuration();
			int multiple = 0;
			if(duration.indexOf("h")>0){
				String hours = duration.substring(0, duration.indexOf("h")).trim();
				multiple = Integer.valueOf(hours) * 2;
			}
			if(duration.indexOf("m")>0){
				multiple += 1;
			}
			
			for(int x=0; x<multiple; x++){
				if(x==0)
					day.set(slotNumber+x, g.getAgeGroup().getName()+" Game: "+g.getHomeTeam()+" vs "+g.getAwayTeam());
				else
					day.set(slotNumber+x, "Game");
			}
			
		}
		return day;
	}
	
	
	// Block Time based on Field Setup Availability
	protected void addBlockedTimes(ArrayList<String> day, Integer startTime, Integer endTime) {
		Calendar times = Calendar.getInstance();
		times.set(Calendar.HOUR_OF_DAY, 9);
		times.set(Calendar.MINUTE, 0);
		times.set(Calendar.SECOND, 0);
		
		Date now = new Date();
		
		for(int x=0; x<26; x++){
			int loopedHour = times.get(Calendar.HOUR_OF_DAY);
			if(now.compareTo(times.getTime())>0){
				day.add("N/A");
			}
			else if(startTime<=loopedHour && endTime>loopedHour){
				day.add(null);
			}else{
				day.add("N/A");
			}
			times.add(Calendar.MINUTE, 30);
		}
	}

	protected void addBlockedDay(ArrayList<String> day) {
		day.add("N/A"); // 9:00
		day.add("N/A"); // 9:30
		day.add("N/A"); // 10:00
		day.add("N/A"); // 10:30
		day.add("N/A"); // 11:00
		day.add("N/A"); // 11:30
		day.add("N/A"); // 12:00
		day.add("N/A"); // 12:30
		day.add("N/A"); // 1:00
		day.add("N/A"); // 1:30
		day.add("N/A"); // 2:00
		day.add("N/A"); // 2:30
		day.add("N/A"); // 3:00
		day.add("N/A"); // 3:30
		day.add("N/A"); // 4:00
		day.add("N/A"); // 4:30
		day.add("N/A"); // 5:00
		day.add("N/A"); // 5:30
		day.add("N/A"); // 6:00
		day.add("N/A"); // 6:30
		day.add("N/A"); // 7:00
		day.add("N/A"); // 7:30
		day.add("N/A"); // 8:00
		day.add("N/A"); // 8:30
		day.add("N/A"); // 9:00
		day.add("N/A"); // 9:30
	}
	
	protected void addBlankDay(ArrayList<String> day) {
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
	}

	protected int getHourSlotNumber(Date d){
		
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
