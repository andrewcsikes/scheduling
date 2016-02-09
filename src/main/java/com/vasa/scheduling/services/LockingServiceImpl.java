package com.vasa.scheduling.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vasa.scheduling.domain.Fields;
import com.vasa.scheduling.domain.Season;
import com.vasa.scheduling.domain.Team;
import com.vasa.scheduling.enums.Classification;
import com.vasa.scheduling.enums.Status;
import com.vasa.scheduling.enums.UserType;

@Service("lockingService")
public class LockingServiceImpl implements LockingService {

	@Autowired
	private ScheduleService service;
	
	@Override
	public boolean getLocked(Team team, Fields field, Date startOfWeek) {
		Season season = service.findSeason(field.getSport());
		if(season == null){
			return false;
		}else if (team==null || team.getSeason() == null){
			return true;
		}else if(team.getSeason().getStatus().equals(Status.INACTIVE)){
			return true;
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
		
		if(team != null && team.getClassification().equals(Classification.NON_VASA)){
			return lockNonVasa(startOfWeek, team.getSeason());
		}
		
		if(!season.getApplySchedulingRules()){
			return false;
		}
				
		if(team !=null && team.getSport().getName().equals("Baseball")){
			return lockBaseball(startOfWeek, team.getSeason());
		}
		else if(team !=null && team.getSport().getName().equals("Softball")){
			return lockSoftball(startOfWeek, team.getSeason());
		}
		return false;
	}
	
	protected boolean lockSeason(Season season, Date startOfWeek){
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
	
	private boolean lockNonVasa(Date startOfWeek, Season season) {
		Calendar today = Calendar.getInstance();
		Calendar week = Calendar.getInstance();
		week.setTime(startOfWeek);
		
		// This week
		if(today.compareTo(week)>0){
			return false;
		}
		
		// The week before
		if(today.compareTo(week)<0){
			if(season.getStartDate() == null){
				return false;
			}
			if(today.get(Calendar.DAY_OF_WEEK)<Calendar.FRIDAY){
				// Lock if before Friday
				return true;
			}
		}
		
		return false;
	}
	
	private boolean lockBaseball(Date startOfWeek, Season season) {
		Calendar today = Calendar.getInstance();
		Calendar week = Calendar.getInstance();
		week.setTime(startOfWeek);
		
		// This week
		if(today.compareTo(week)>0){
			return false;
		}
		
		// The week before
		if(today.compareTo(week)<0 && seasonStarted(season, startOfWeek)){
			if(today.get(Calendar.DAY_OF_WEEK)<Calendar.THURSDAY){
				// Lock if before Thursday
				return true;
			}
		}
		
		return false;
	}

	private boolean lockSoftball(Date startOfWeek, Season season) {
		Calendar today = Calendar.getInstance();
		Calendar week = Calendar.getInstance();
		week.setTime(startOfWeek);
		
		// This week
		if(today.compareTo(week)>0){
			return false;
		}
		
		// The week before
		if(today.compareTo(week)<0 && seasonStarted(season, startOfWeek)){
			if(today.get(Calendar.DAY_OF_WEEK)<Calendar.WEDNESDAY){
				// Lock if before Thursday
				return true;
			}
		}
		
		return false;
	}
	
	private boolean seasonStarted(Season season, Date startOfWeek) {
		// see if the last day of the week is before the season startDate
		Calendar today = Calendar.getInstance();
		today.setTime(season.getStartDate());
		today.set(Calendar.MINUTE, 0);
		today.set(Calendar.HOUR, 0);
		today.set(Calendar.SECOND, 0);
		if(startOfWeek.after(today.getTime())){
			return true;
		}
		return false;
	}
	
	@Override
	public void setBlockedTimesBasedOnRules(Fields field, Team team, Date date, ArrayList<String> day) {
		Calendar today = Calendar.getInstance();
		
		Calendar calDay = Calendar.getInstance();
		calDay.setTime(date);
		
		Calendar week = Calendar.getInstance();
		week.setTime(date);
		week.set(Calendar.DAY_OF_WEEK, 1);
		
		Calendar twoWeeks = Calendar.getInstance();
		twoWeeks.add(Calendar.WEEK_OF_YEAR, 2);
		twoWeeks.set(Calendar.DAY_OF_WEEK, 1);
		twoWeeks.add(Calendar.DAY_OF_YEAR, -1);
		
		if(today.after(week)){
			// This week, do nothing
		}else{
			if(field.getSport().getName().equals("Baseball") && 
					team != null && 
					team.getSeason().getApplySchedulingRules()){
				// run baseball rules
				if(calDay.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY
						|| calDay.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY){
					// No rules apply
				}else if(today.get(Calendar.DAY_OF_WEEK)<=Calendar.THURSDAY || week.after(twoWeeks)){
					// don't allow older teams to schedule anything before 7:00
					if(field.getName().contains("FM") && 
							!team.getCoach().getUserType().equals(UserType.ADMIN) &&
							(team.getAgeGroup().getName().equals("10U") 
								|| team.getAgeGroup().getName().equals("12U")
								|| team.getAgeGroup().getName().equals("14U"))){
						day.set(16, "Reserved For Younger Teams.");
						day.set(17, "Reserved For Younger Teams.");
						day.set(18, "Reserved For Younger Teams.");
						day.set(19, "Reserved For Younger Teams.");
					}
					else if(field.getName().contains("FM") && 
							!team.getCoach().getUserType().equals(UserType.ADMIN) &&
							(team.getAgeGroup().getName().equals("6U") 
								|| team.getAgeGroup().getName().equals("8U"))){
						day.set(22, "Reserved For Older Teams.");
						day.set(23, "Reserved For Older Teams.");
						day.set(24, "Reserved For Older Teams.");
						day.set(25, "Reserved For Older Teams.");
					}
				}
			}else if(field.getSport().getName().equals("Softball") && 
					team != null && 
					team.getSeason().getApplySchedulingRules()){
				if(calDay.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY
						|| calDay.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY){
					// No rules apply
				}else if(today.get(Calendar.DAY_OF_WEEK)<=Calendar.WEDNESDAY || week.after(twoWeeks)){
					// don't allow older teams to schedule little east
					if(field.getName().contains("Little") &&
							 !team.getCoach().getUserType().equals(UserType.ADMIN) &&
							(team.getAgeGroup().getName().equals("10U") 
								|| team.getAgeGroup().getName().equals("12U")
								|| team.getAgeGroup().getName().equals("14U"))){
						day.set(14, "Reserved For Younger Teams.");
						day.set(15, "Reserved For Younger Teams.");
						day.set(16, "Reserved For Younger Teams.");
						day.set(17, "Reserved For Younger Teams.");
						day.set(18, "Reserved For Younger Teams.");
						day.set(19, "Reserved For Younger Teams.");
						day.set(20, "Reserved For Younger Teams.");
						day.set(21, "Reserved For Younger Teams.");
						day.set(22, "Reserved For Younger Teams.");
						day.set(23, "Reserved For Younger Teams.");
						day.set(24, "Reserved For Younger Teams.");
						day.set(25, "Reserved For Younger Teams.");
					}else if(field.getName().contains("Big") && 
							!team.getCoach().getUserType().equals(UserType.ADMIN) &&
							(team.getAgeGroup().getName().equals("6U") 
								|| team.getAgeGroup().getName().equals("8U"))){
						day.set(14, "Reserved For Older Teams.");
						day.set(15, "Reserved For Older Teams.");
						day.set(16, "Reserved For Older Teams.");
						day.set(17, "Reserved For Older Teams.");
						day.set(18, "Reserved For Older Teams.");
						day.set(19, "Reserved For Older Teams.");
						day.set(20, "Reserved For Older Teams.");
						day.set(21, "Reserved For Older Teams.");
						day.set(22, "Reserved For Older Teams.");
						day.set(23, "Reserved For Older Teams.");
						day.set(24, "Reserved For Older Teams.");
						day.set(25, "Reserved For Older Teams.");
					}
				}
			}
		}
		
	}

}
