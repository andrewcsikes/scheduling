package com.vasa.scheduling.interfaces.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
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
import com.vasa.scheduling.domain.Season;
import com.vasa.scheduling.domain.Sport;
import com.vasa.scheduling.domain.Team;
import com.vasa.scheduling.domain.User;
import com.vasa.scheduling.enums.Classification;
import com.vasa.scheduling.enums.UserType;
import com.vasa.scheduling.services.EmailService;
import com.vasa.scheduling.services.ScheduleService;
import com.vasa.scheduling.services.TeamService;

@Controller
@RequestMapping("/schedule/calendar")
public class ScheduleController extends DefaultHandlerController {

	@Autowired
	private EmailService es;
	
	@Autowired
	private ScheduleService service;
	
	@Autowired
	private TeamService teamService;
	
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

	private void buildCalendar(String date, Model model, User user) {
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
		
		List<Fields> fields = null;
		if(user != null && user.getTeam() != null){
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
			// Get the fields for the sport(s) that is active
			List<Season> activeSeasons = service.findActiveSeasons();
			for(Season season : activeSeasons){
				if(fields==null){
					fields = service.findAllFields(season.getSport());
				}else{
					fields.addAll(service.findAllFields(season.getSport()));
				}
			}
			if(activeSeasons.size() == 0){
				fields = service.findAllFields();
			}
		}
		
		// schedule contains the entire schedule for every field
		HashMap<String, HashMap<String,ArrayList<String>>> schedule = new HashMap<String, HashMap<String,ArrayList<String>>>();
		
		boolean anylocked = false;
		for(Fields field: fields){
			
			boolean locked = getLocked(user.getTeam(), field, startOfWeek);
			anylocked=anylocked||locked;
			model.addAttribute(field.getName()+"locked",locked);
			
			sunday.setTime(startDate);
			sunday.set(Calendar.MINUTE, 0);
			sunday.set(Calendar.HOUR, 0);
			sunday.set(Calendar.DAY_OF_WEEK, 1);
			// week - key is the day, value is the fields
			HashMap<String,ArrayList<String>> week = new HashMap<String, ArrayList<String>>();
			Team team = user.getTeam();
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
	}
	
	private boolean getLocked(Team team, Fields field, Date startOfWeek) {
		
		Season season = service.findSeason(field.getSport());
		if(season == null){
			return false;
		}else if(season.getSport() != null){
			if(lockSport(team, season, field, startOfWeek)){
				return true;
			}
		}
		return false;
	}
		
	private boolean lockSport(Team team, Season season, Fields field, Date startOfWeek) {
		boolean lock = lockSeason(season, startOfWeek);
		if(lock){
			return true;
		}
		
		if(!season.getApplySchedulingRules()){
			return false;
		}
		
		if(team != null && team.getClassification().equals(Classification.NON_VASA)){
			return lockNonVasa(startOfWeek);
		}
		if(team !=null && team.getSport().getName().equals("Baseball")){
			return lockBaseball(startOfWeek);
		}
		else if(team !=null && team.getSport().getName().equals("Softball")){
			//return lockSoftball(startOfWeek);
		}
		return false;
	}
	
	private boolean lockNonVasa(Date startOfWeek) {
		Calendar today = Calendar.getInstance();
		Calendar week = Calendar.getInstance();
		week.setTime(startOfWeek);
		
		// This week
		if(today.compareTo(week)>0){
			return false;
		}
		
		// The week before
		if(today.compareTo(week)<0){
			if(today.get(Calendar.DAY_OF_WEEK)<Calendar.SATURDAY){
				// Lock if before Thursday
				return true;
			}
		}
		
		return false;
	}
	
	private boolean lockBaseball(Date startOfWeek) {
		Calendar today = Calendar.getInstance();
		Calendar week = Calendar.getInstance();
		week.setTime(startOfWeek);
		
		// This week
		if(today.compareTo(week)>0){
			return false;
		}
		
		// The week before
		if(today.compareTo(week)<0){
			if(today.get(Calendar.DAY_OF_WEEK)<Calendar.THURSDAY){
				// Lock if before Thursday
				return true;
			}else if(today.get(Calendar.DAY_OF_WEEK)==Calendar.FRIDAY){
				// Lock if Friday
				return true;
			}
		}
		
		return false;
	}
	
	private boolean lockSoftball(Date startOfWeek) {
		Calendar today = Calendar.getInstance();
		Calendar week = Calendar.getInstance();
		week.setTime(startOfWeek);
		
		// This week
		if(today.compareTo(week)>0){
			return false;
		}
		
		// The week before
		if(today.compareTo(week)<0){
			if(today.get(Calendar.DAY_OF_WEEK)<Calendar.WEDNESDAY){
				// Lock if before Thursday
				return true;
			}else if(today.get(Calendar.DAY_OF_WEEK)==Calendar.THURSDAY ||
					today.get(Calendar.DAY_OF_WEEK)==Calendar.FRIDAY){
				// Lock if Friday
				return true;
			}
		}
		
		return false;
	}

	private boolean lockSeason(Season season, Date startOfWeek){
		if(season.getStartDate() == null){
			return false;
		}
		
		// see if the last day of the week is before the season startDate
		Calendar today = Calendar.getInstance();
		today.setTime(season.getStartDate());
		today.set(Calendar.MINUTE, 0);
		today.set(Calendar.HOUR, 0);
		today.set(Calendar.SECOND, 0);
		if(startOfWeek.after(today.getTime())){
			// see if the the start week > than today + 7
			today = Calendar.getInstance();
			Date d = new Date();
			today.setTime(d);
			today.set(Calendar.MINUTE, 0);
			today.set(Calendar.HOUR, 0);
			today.add(Calendar.DAY_OF_YEAR, 7);
			
			if(startOfWeek.after(today.getTime())){
				return true;
			}
		}
		
		return false;
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
			
			if(schedule == null && validateRequest(model, user.getTeam(), calendarDay)){
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
		boolean validate = validateWeeklyPracticeLimit(model, team, calendarDay);
		if(validate){
			validate = validateDailyPracticeLimit(model, team, calendarDay);
		}
		return validate;
	}

	private boolean validateWeeklyPracticeLimit(Model model, Team team, Date calendarDay) {
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
	
	private boolean validateDailyPracticeLimit(Model model, Team team, Date calendarDay) {
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
				
				// If date is current week, email league coaches.
				Calendar today = Calendar.getInstance();
				Calendar week = Calendar.getInstance();
				week.setTime(schedule.getDate());
				week.set(Calendar.DAY_OF_WEEK,1);
				if(today.compareTo(week)>0){
					try{
						// loop through all the coaches for the sport
						for(Team t : teamService.findTeamsBySport(schedule.getField().getSport())){
							User u = t.getCoach();
							String message = "The practice spot for "+schedule.getField().getName()+" at "+formatter.format(schedule.getDate())+" was previously scheduled, but is now available.";
							String emailAddress = u.getEmailAddress();
							if(emailAddress != null){
								es.sendEmail(emailAddress, message);
							}
						}
					}catch(Exception e){
						model.addAttribute("error", e.getCause() +": "+e.getMessage());
					}
				}
			}else if(schedule != null && (user.getUserType().equals(UserType.ADMIN) ||
					user.getUserType().equals(UserType.COMMISSIONER))){
				service.delete(schedule);
				// email coach, ADMIN deleted his schedule
				try{
					String message = "Your practice for "+schedule.getField().getName()+" at "+formatter.format(schedule.getDate())+" was removed by the "+user.getFirstName()+" "+user.getLastName();
					String emailAddress = schedule.getTeam().getCoach().getEmailAddress();
					if(emailAddress != null){
						es.sendEmail(emailAddress, message);
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
	
	private ArrayList<String> getFieldDay(Date date, Fields field, Team team) {
		
		ArrayList<String> day = new ArrayList<String>();
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
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
		
		setBlockedTimesBasedOnRules(field, team, date, day);
		
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
	
	private void setBlockedTimesBasedOnRules(Fields field, Team team, Date date, ArrayList<String> day) {
		Calendar today = Calendar.getInstance();
		
		Calendar calDay = Calendar.getInstance();
		calDay.setTime(date);
		
		Calendar week = Calendar.getInstance();
		week.setTime(date);
		week.set(Calendar.DAY_OF_WEEK, 1);
		
		if(today.compareTo(week)>0){
			// This week, do nothing
		}else{
			if(field.getSport().getName().equals("Baseball") && 
					team != null && 
					team.getSeason().getApplySchedulingRules()){
				// run baseball rules
				if(calDay.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY
						|| calDay.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY){
					// No rules apply
				}else if(today.get(Calendar.DAY_OF_WEEK)==Calendar.THURSDAY){
					// don't allow older teams to schedule anything before 7:00
					if(field.getName().contains("FM") &&
							(team.getAgeGroup().getName().equals("10U") 
								|| team.getAgeGroup().getName().equals("12U")
								|| team.getAgeGroup().getName().equals("14U"))){
						day.set(16, "Reserved For Younger Teams.");
						day.set(17, "Reserved For Younger Teams.");
						day.set(18, "Reserved For Younger Teams.");
						day.set(19, "Reserved For Younger Teams.");
					}
				}
			}else if(field.getSport().getName().equals("Softball") && 
					team != null && 
					team.getSeason().getApplySchedulingRules()){
				if(calDay.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY
						|| calDay.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY){
					// No rules apply
				}else if(today.get(Calendar.DAY_OF_WEEK)<=Calendar.WEDNESDAY){
					// don't allow older teams to schedule little east
					if(field.getName().contains("Little") &&
							(team.getAgeGroup().getName().equals("10U") 
								|| team.getAgeGroup().getName().equals("12U")
								|| team.getAgeGroup().getName().equals("14U"))){
						day.set(16, "Reserved For Younger Teams.");
						day.set(17, "Reserved For Younger Teams.");
						day.set(18, "Reserved For Younger Teams.");
						day.set(19, "Reserved For Younger Teams.");
						day.set(20, "Reserved For Younger Teams.");
						day.set(21, "Reserved For Younger Teams.");
						day.set(22, "Reserved For Younger Teams.");
						day.set(23, "Reserved For Younger Teams.");
					}else if(field.getName().contains("Big") &&
							(team.getAgeGroup().getName().equals("6U") 
								|| team.getAgeGroup().getName().equals("8U"))){
						day.set(16, "Reserved For Older Teams.");
						day.set(17, "Reserved For Older Teams.");
						day.set(18, "Reserved For Older Teams.");
						day.set(19, "Reserved For Older Teams.");
						day.set(20, "Reserved For Older Teams.");
						day.set(21, "Reserved For Older Teams.");
						day.set(22, "Reserved For Older Teams.");
						day.set(23, "Reserved For Older Teams.");
					}
				}
			}
		}
		
	}

	private void addBlockedTimes(ArrayList<String> day, Integer startTime, Integer endTime) {
		Calendar times = Calendar.getInstance();
		times.set(Calendar.HOUR_OF_DAY, 9);
		times.set(Calendar.MINUTE, 0);
		times.set(Calendar.SECOND, 0);
		
		for(int x=0; x<26; x++){
			int currentHour = times.get(Calendar.HOUR_OF_DAY);
			if(startTime<=currentHour && endTime>currentHour){
				day.add(null);
			}else{
				day.add("N/A");
			}
			times.add(Calendar.MINUTE, 30);
		}
	}

	private void addBlockedDay(ArrayList<String> day) {
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
	
	private void addBlankDay(ArrayList<String> day) {
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