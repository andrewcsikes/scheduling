package com.vasa.scheduling.interfaces.web;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import com.vasa.scheduling.domain.Season;
import com.vasa.scheduling.domain.Sport;
import com.vasa.scheduling.domain.Team;
import com.vasa.scheduling.domain.User;
import com.vasa.scheduling.services.EmailService;
import com.vasa.scheduling.services.ScheduleService;
import com.vasa.scheduling.services.TeamService;

@Controller
@RequestMapping("/schedule/basketball")
public class BasketballScheduleController extends ScheduleController {

	@Autowired
	private EmailService es;
	
	@Autowired
	private ScheduleService service;
	
	@Autowired
	private TeamService teamService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String list(@RequestParam(required=false, value="date") String date, Model model, HttpServletRequest request) {
		
		User user = verifyUser(request.getSession());
		model.addAttribute("user", user);
		
		if(user== null){
			return "login";
		}
		
		buildCalendar(date, model, user);
		
	    return "schedule/basketball";
	}

	
	protected List<Fields> getFields(User user){
		Sport sport = teamService.findSportById(3);
		return service.findAllFields(sport);
	}
	
	protected ArrayList<String> getFieldDay(Date date, Fields field, Team team) {
		
		ArrayList<String> day = new ArrayList<String>();
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		if(cal.get(Calendar.DAY_OF_YEAR)>327 && cal.get(Calendar.DAY_OF_YEAR)<333){
			addBlockedDay(day);
		}else if(cal.get(Calendar.DAY_OF_YEAR)>355 && cal.get(Calendar.DAY_OF_YEAR)<366){
			addBlockedDay(day);
		}else if(cal.get(Calendar.DAY_OF_YEAR)>=1 && cal.get(Calendar.DAY_OF_YEAR)<4){
			addBlockedDay(day);
		}else if(cal.get(Calendar.DAY_OF_YEAR)>67 && cal.get(Calendar.DAY_OF_YEAR)<73){
				addBlockedDay(day);
		}else if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
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
		if(season != null && season.getApplySchedulingRules()){
			setBlockedTimesBasedOnRules(field, team, date, day);
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
	
	protected void setBlockedTimesBasedOnRules(Fields field, Team team, Date date, ArrayList<String> day) {
		Calendar today = Calendar.getInstance();
		
		Calendar calDay = Calendar.getInstance();
		calDay.setTime(date);
		
		Calendar week = Calendar.getInstance();
		week.setTime(date);
		week.set(Calendar.DAY_OF_WEEK, 1);
		
		if(today.compareTo(week)>0){
			// This week, do nothing
		}else{
			if(team != null && 
				team.getSeason().getApplySchedulingRules()){
				if(today.get(Calendar.DAY_OF_WEEK)==Calendar.THURSDAY){
					// don't allow older teams to schedule anything before 7:00
					if(field.getName().contains("FM") &&
							(team.getAgeGroup().getName().equals("4th Grade") 
								|| team.getAgeGroup().getName().equals("5th Grade")
								|| team.getAgeGroup().getName().equals("6th Grade"))){
						day.set(16, "Reserved For Younger Teams.");
						day.set(17, "Reserved For Younger Teams.");
						day.set(18, "Reserved For Younger Teams.");
						day.set(19, "Reserved For Younger Teams.");
					}
				}
			}
		}
		
	}

	protected void addBlockedTimes(ArrayList<String> day, Integer startTime, Integer endTime) {
		Calendar times = Calendar.getInstance();
		times.set(Calendar.HOUR_OF_DAY, 17);
		times.set(Calendar.MINUTE, 0);
		times.set(Calendar.SECOND, 0);
		
		for(int x=0; x<10; x++){
			int currentHour = times.get(Calendar.HOUR_OF_DAY);
			if(startTime<=currentHour && endTime>currentHour){
				day.add(null);
			}else{
				day.add("N/A");
			}
			times.add(Calendar.MINUTE, 30);
		}
	}

	protected void addBlockedDay(ArrayList<String> day) {
		//day.add("N/A"); // 9:00
		//day.add("N/A"); // 9:30
		//day.add("N/A"); // 10:00
		//day.add("N/A"); // 10:30
		//day.add("N/A"); // 11:00
		//day.add("N/A"); // 11:30
		//day.add("N/A"); // 12:00
		//day.add("N/A"); // 12:30
		//day.add("N/A"); // 1:00
		//day.add("N/A"); // 1:30
		//day.add("N/A"); // 2:00
		//day.add("N/A"); // 2:30
		//day.add("N/A"); // 3:00
		//day.add("N/A"); // 3:30
		//day.add("N/A"); // 4:00
		//day.add("N/A"); // 4:30
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
		/**
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
		***/
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
		timeSlot.set(Calendar.HOUR_OF_DAY, 17);
		
		int x = 0;
		
		while(timeSlot.getTime().before(d)){
			x++;
			timeSlot.add(Calendar.MINUTE, 30);
		}
		
		return x;
	}
}