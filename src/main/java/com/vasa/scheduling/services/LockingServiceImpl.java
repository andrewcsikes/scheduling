package com.vasa.scheduling.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vasa.scheduling.domain.FieldRule;
import com.vasa.scheduling.domain.Fields;
import com.vasa.scheduling.domain.Season;
import com.vasa.scheduling.domain.Team;
import com.vasa.scheduling.enums.Classification;
import com.vasa.scheduling.enums.Operation;
import com.vasa.scheduling.enums.Status;

@Service("lockingService")
public class LockingServiceImpl implements LockingService {

	@Autowired
	private ScheduleService service;
	
	@Override
	public boolean getLocked(Team team, Fields field, Date startOfWeek) {
		boolean lock = false;
		
		Season season = service.findSeason(field.getSport());
		if(season == null){
			return false;
		}else{
			lock = lockSeason(season, startOfWeek);
		}
		
		if (!lock &&
				(team==null || team.getSeason() == null)){
			lock = true;
		}
		
		if(!lock && team.getSeason().getStatus().equals(Status.INACTIVE)){
			lock = true;
		}
		
		if(!lock && season.getSport() != null){
			lock = lockSport(team, season, field, startOfWeek);
		}
		
		return lock;
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
	
	private boolean lockSport(Team team, Season season, Fields field, Date startOfWeek) {
//		if(team != null && team.getClassification().equals(Classification.NON_VASA)){
//			return lockNonVasa(startOfWeek, team);
//		}
		
		if(!season.getApplySchedulingRules() && 
				team != null && 
				!team.getClassification().equals(Classification.NON_VASA)){
			return false;
		}
		
		if(team.getSport().getDayOfWeek() != null){
			Calendar today = Calendar.getInstance();
			Calendar week = Calendar.getInstance();
			week.setTime(startOfWeek);
			
			int lockDay = team.getSport().getDayOfWeek().getCode();
			if(team.getClassification().equals(Classification.NON_VASA) &&
					team.getSport().getNonVasaDayOfWeek() != null){
				lockDay = team.getSport().getNonVasaDayOfWeek().getCode();
			}else if(team.getSport().getId() != field.getSport().getId() &&  
					!field.getName().contains("Batting")){
				// softball can't schedule baseball fields until after baseball gets a chance.
				lockDay++;
			}
			
			// This week
			if(today.compareTo(week)>0){
				return false;
			}
			
			if(today.get(Calendar.DAY_OF_WEEK) < lockDay){
				return true;
			}
			else if(today.get(Calendar.DAY_OF_WEEK) == lockDay && team.getSport().getTime() != null){
				if(today.get(Calendar.HOUR_OF_DAY) < team.getSport().getTime().intValue()){
					return true;
				}
			}
		}
		
//		if(team !=null && team.getSport().getName().equals("Baseball")){
//			return lockBaseball(startOfWeek, team.getSeason());
//		}
//		else if(team !=null && team.getSport().getName().equals("Softball")){
//			return lockSoftball(startOfWeek, team.getSeason());
//		}
		
		return false;
	}
	
	private boolean lockNonVasa(Date startOfWeek, Team team) {
		Calendar today = Calendar.getInstance();
		Calendar week = Calendar.getInstance();
		week.setTime(startOfWeek);
		
		// This week
		if(today.compareTo(week)>0){
			return false;
		}
		
		// The week before
		if(today.compareTo(week)<0){
			if(team.getSeason().getStartDate() == null){
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
//		if(today.compareTo(week)<0 && seasonStarted(season, startOfWeek)){
//			if(today.get(Calendar.DAY_OF_WEEK)<Calendar.THURSDAY){
//				// Lock if before Thursday
//				return true;
//			}
//		}
		
		if(today.get(Calendar.DAY_OF_WEEK)<Calendar.WEDNESDAY){
			// Lock if before Wednesday
			return true;
		}
		else if(today.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY){
			if(today.get(Calendar.HOUR_OF_DAY) < 20){
				// Lock if before 8:00 PM
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
//		if(today.compareTo(week)<0 && seasonStarted(season, startOfWeek)){
//			if(today.get(Calendar.DAY_OF_WEEK)<Calendar.WEDNESDAY){
//				// Lock if before Thursday
//				return true;
//			}
//		}
		
		if(today.get(Calendar.DAY_OF_WEEK)<Calendar.TUESDAY){
			// Lock if before Tuesday
			return true;
		}
		else if(today.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY){
			if(today.get(Calendar.HOUR_OF_DAY) < 20){
				// Lock if before 8:00 PM
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public void lockFieldTimesBasedOnRules(Fields field, Team team, Date date, ArrayList<String> day){
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
		
		if(field == null || team == null){
			return;
		}
		
		if(today.after(week) || 
				field.getFieldRules() == null || 
				team.getSport().getDayOfWeek() == null ||
				!team.getSeason().getApplySchedulingRules()){
			return;
		}
		
		int lockDay = field.getSport().getDayOfWeek().getCode();
		int unlockDay = field.getSport().getDayOfWeek().getCode() + 2;
		if(team.getClassification().equals(Classification.NON_VASA) && 
				field.getSport().getNonVasaDayOfWeek() != null){
			lockDay = field.getSport().getNonVasaDayOfWeek().getCode();
		}else if(team.getSport().getId() != field.getSport().getId() &&  
			!field.getName().contains("Batting")){
			// softball can't schedule baseball fields until after baseball gets a chance.
			lockDay++;
		}
		
		if(today.get(Calendar.DAY_OF_WEEK)<lockDay){
			for(int x=0; x<=25; x++){
				day.set(x, "N/A");
			}
			return;
		}
		
		for(FieldRule rule: field.getFieldRules()){
			if(rule.getAgeGroup().getId() == team.getAgeGroup().getId()){
				if(calDay.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY
						|| calDay.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY){
					// No rules apply
				}else if(today.get(Calendar.DAY_OF_WEEK)<=unlockDay || week.after(twoWeeks)){
					if(rule.getOperation() == Operation.BEFORE){
						for(int x=0; x<=25; x++){
							int hourValue = x/2+9;
							if(hourValue<rule.getTime()){
								day.set(x, rule.getMessage()!=null?rule.getMessage():"Reserved");
							}
						}
					}else if(rule.getOperation() == Operation.AFTER){
						for(int x=0; x<=25; x++){
							int hourValue = x/2+9;
							if(hourValue>=rule.getTime()){
								day.set(x, rule.getMessage()!=null?rule.getMessage():"Reserved");
							}
						}
					}
				}
			}
		}
	}
	
	@Override
	public void setBlockedTimesBasedOnRules(Fields field, Team team, Date date, ArrayList<String> day) {
		lockFieldTimesBasedOnRules(field, team, date, day);
//		Calendar today = Calendar.getInstance();
//		
//		Calendar calDay = Calendar.getInstance();
//		calDay.setTime(date);
//		
//		Calendar week = Calendar.getInstance();
//		week.setTime(date);
//		week.set(Calendar.DAY_OF_WEEK, 1);
//		
//		Calendar twoWeeks = Calendar.getInstance();
//		twoWeeks.add(Calendar.WEEK_OF_YEAR, 2);
//		twoWeeks.set(Calendar.DAY_OF_WEEK, 1);
//		twoWeeks.add(Calendar.DAY_OF_YEAR, -1);
//		
//		if(today.after(week)){
//			// This week, do nothing
//		}else{
//			if(field.getSport().getName().equals("Baseball") && 
//					team != null && 
//					team.getSeason().getApplySchedulingRules()){
//				// run baseball rules
//				if(calDay.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY
//						|| calDay.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY){
//					// No rules apply
//				}else if(today.get(Calendar.DAY_OF_WEEK)<=Calendar.THURSDAY || week.after(twoWeeks)){
//					// don't allow older teams to schedule anything before 7:00
//					if(field.getName().contains("FM") && 
//							!team.getCoach().getUserType().equals(UserType.ADMIN) &&
//							(team.getAgeGroup().getName().equals("10U") 
//								|| team.getAgeGroup().getName().equals("12U")
//								|| team.getAgeGroup().getName().equals("14U"))){
//						day.set(16, "Reserved For Younger Teams.");
//						day.set(17, "Reserved For Younger Teams.");
//						day.set(18, "Reserved For Younger Teams.");
//						day.set(19, "Reserved For Younger Teams.");
//					}
//					else if(field.getName().contains("FM") && 
//							!team.getCoach().getUserType().equals(UserType.ADMIN) &&
//							(team.getAgeGroup().getName().equals("6U") 
//								|| team.getAgeGroup().getName().equals("8U"))){
//						day.set(22, "Reserved For Older Teams.");
//						day.set(23, "Reserved For Older Teams.");
//						day.set(24, "Reserved For Older Teams.");
//						day.set(25, "Reserved For Older Teams.");
//					}
//				}
//			}else if(field.getSport().getName().equals("Softball") && 
//					team != null && 
//					team.getSeason().getApplySchedulingRules()){
//				if(calDay.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY
//						|| calDay.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY){
//					// No rules apply
//				}else if(today.get(Calendar.DAY_OF_WEEK)<=Calendar.WEDNESDAY || week.after(twoWeeks)){
//					// don't allow older teams to schedule little east
//					if(field.getName().contains("Little") &&
//							 !team.getCoach().getUserType().equals(UserType.ADMIN) &&
//							(team.getAgeGroup().getName().equals("10U") 
//								|| team.getAgeGroup().getName().equals("12U")
//								|| team.getAgeGroup().getName().equals("14U"))){
//						day.set(14, "Reserved For Younger Teams.");
//						day.set(15, "Reserved For Younger Teams.");
//						day.set(16, "Reserved For Younger Teams.");
//						day.set(17, "Reserved For Younger Teams.");
//						day.set(18, "Reserved For Younger Teams.");
//						day.set(19, "Reserved For Younger Teams.");
//						day.set(20, "Reserved For Younger Teams.");
//						day.set(21, "Reserved For Younger Teams.");
//						day.set(22, "Reserved For Younger Teams.");
//						day.set(23, "Reserved For Younger Teams.");
//						day.set(24, "Reserved For Younger Teams.");
//						day.set(25, "Reserved For Younger Teams.");
//					}else if(field.getName().contains("Big") && 
//							!team.getCoach().getUserType().equals(UserType.ADMIN) &&
//							(team.getAgeGroup().getName().equals("6U") 
//								|| team.getAgeGroup().getName().equals("8U"))){
//						day.set(14, "Reserved For Older Teams.");
//						day.set(15, "Reserved For Older Teams.");
//						day.set(16, "Reserved For Older Teams.");
//						day.set(17, "Reserved For Older Teams.");
//						day.set(18, "Reserved For Older Teams.");
//						day.set(19, "Reserved For Older Teams.");
//						day.set(20, "Reserved For Older Teams.");
//						day.set(21, "Reserved For Older Teams.");
//						day.set(22, "Reserved For Older Teams.");
//						day.set(23, "Reserved For Older Teams.");
//						day.set(24, "Reserved For Older Teams.");
//						day.set(25, "Reserved For Older Teams.");
//					}
//				}
//			}
//		}
	}
}
