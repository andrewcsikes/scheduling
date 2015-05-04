package com.vasa.scheduling.services;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vasa.scheduling.domain.FieldSchedule;
import com.vasa.scheduling.domain.Fields;
import com.vasa.scheduling.domain.Game;
import com.vasa.scheduling.domain.Log;
import com.vasa.scheduling.domain.Season;
import com.vasa.scheduling.domain.Sport;
import com.vasa.scheduling.domain.Team;
import com.vasa.scheduling.enums.Status;
import com.vasa.scheduling.repositiories.FieldRepository;
import com.vasa.scheduling.repositiories.FieldScheduleRepository;
import com.vasa.scheduling.repositiories.GameRepository;
import com.vasa.scheduling.repositiories.LogRepository;
import com.vasa.scheduling.repositiories.SeasonRepository;
import com.vasa.scheduling.repositiories.SportRepository;

@Service("scheduleService")
public class ScheduleServiceImpl implements ScheduleService {

	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private FieldScheduleRepository repo;
	
	@Autowired
	private LogRepository logRepo;
	
	@Autowired
	private FieldRepository fieldRepo;
	
	@Autowired
	private GameRepository gameRepo;
	
	@Autowired
	private SeasonRepository seasonRepo;
	
	@Autowired
	private SportRepository sportRepo;
	
	@Override
	public FieldSchedule save(FieldSchedule schedule) {
		return repo.save(schedule);
	}
	
	@Override
	public void delete(FieldSchedule schedule) {
		repo.delete(schedule);
	}

	@Override
	public List<FieldSchedule> findByDayField(Date date, String field) {
		return repo.findByDayAndFieldName(date, field);
	}
	
	@Override
	public FieldSchedule findByDateField(Date date, String field) {
		return repo.findByDateAndFieldName(date, field);
	}

	@Override
	public FieldSchedule findById(Integer id) {
		return repo.findById(id);
	}

	@Override
	public Fields findFieldByName(String field) {
		return fieldRepo.findByName(field);
	}

	@Override
	public List<Fields> findAllFields() {
		return fieldRepo.findByStatusOrderByIdAsc(Status.ACTIVE);
	}
	
	@Override
	public List<Fields> findAllFields(Sport sport) {
		return fieldRepo.findBySportAndStatusOrderByIdAsc(sport, Status.ACTIVE);
	}
	
	@Override
	public List<Season> findActiveSeasons(){
		return seasonRepo.findByStatus(Status.ACTIVE);
	}

	@Override
	public Sport findSportByName(String name){
		return sportRepo.findByName(name);
	}
	

	@Override
	public Season findSeasonById(Integer id){
		return seasonRepo.findById(id);
	}

	@Override
	public List<FieldSchedule> findByMonth(Date date) {
		return repo.findByMonth(date);
	}

	@Override
	public List<FieldSchedule> findByMonthAndTeam(Date date, Team team) {
		return repo.findByMonthAndTeam(date, team);
	}

	@Override
	public List<FieldSchedule> findScheduleForWeek(Team team, Date calendarDay) {
		return repo.findScheduleForWeek(team, calendarDay);
	}
	
	@Override
	public List<FieldSchedule> findScheduleForDay(Team team, Date calendarDay) {
		return repo.findScheduleForDay(team, calendarDay);
	}

	@Override
	public Season findSeason(Sport sport) {
		return seasonRepo.findBySportAndStatus(sport, Status.ACTIVE);
	}

	@Override
	public List<FieldSchedule> findByFilter(Team team, Fields field, String filterClass, Date time) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
		
		String query = "Select s from FieldSchedule s where ";
		String whereClause = "month(date)=month(STR_TO_DATE('"+sdf.format(time)+"','%m-%d-%Y'))";
		
		if(team != null){
			whereClause += " and team.id="+team.getId();
		}
		if(field != null){
			whereClause += " and field.id="+field.getId();
		}
//		if(filterClass != null && !filterClass.equals("0")){
//			whereClause += " and (team is null or team.classification.code="+filterClass+")";
//		}
		
		String orderBy = " order by date";
		
		List<FieldSchedule> results = (List<FieldSchedule>)em.createQuery(query+whereClause+orderBy, FieldSchedule.class).getResultList();
		
		return results;
	}

	@Override
	public List<Game> findGamesByDayField(Date date, String field) {
		return gameRepo.findByDayAndFieldName(date, field);
	}

	@Override
	public List<Game> findGameByMonth(Date date) {
		return gameRepo.findByMonth(date);
	}

	@Override
	public List<Game> findGamesByFilter(Team team, Fields field, String filterClass, Date time) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
		
		String query = "Select s from Game s where ";
		String whereClause = "month(date)=month(STR_TO_DATE('"+sdf.format(time)+"','%m-%d-%Y'))";
		
		if(team != null){
			whereClause += " and (homeTeam='"+team.getName()+"' or awayTeam='"+team.getName()+"')";
			whereClause += " and ageGroup="+team.getAgeGroup().getId();
			whereClause += " and field.sport="+team.getSport().getId();
		}
		if(field != null){
			whereClause += " and field.id="+field.getId();
		}
//		if(filterClass != null && !filterClass.equals("0")){
//			whereClause += " and (team is null or team.classification.code="+filterClass+")";
//		}
		
		String orderBy = " order by date";
		
		List<Game> results = (List<Game>)em.createQuery(query+whereClause+orderBy, Game.class).getResultList();
		
		return results;
	}

	@Override
	public Game save(Game schedule) {
		return gameRepo.save(schedule);
	}

	@Override
	public FieldSchedule getLastActive() {
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
		
		Date d = new Date();
		Date maxDate = null;
		FieldSchedule fs = null;
		
		String query = "Select s from FieldSchedule s where ";
		String whereClause = "month(date)=month(STR_TO_DATE('"+sdf.format(d)+"','%m-%d-%Y'))";
				
		List<FieldSchedule> results = (List<FieldSchedule>)em.createQuery(query+whereClause, FieldSchedule.class).getResultList();
		
		if(results == null || results.size()<=0){
			Calendar c = Calendar.getInstance();
			c.setTime(d);
			c.add(Calendar.MONTH, -1);
			whereClause = "month(date)=month(STR_TO_DATE('"+sdf.format(d)+"','%m-%d-%Y'))";
			
			results = (List<FieldSchedule>)em.createQuery(query+whereClause, FieldSchedule.class).getResultList();
		}
		
		if(results == null || results.size()<=0){
			return null;
		}
		
		for(FieldSchedule sch : results){
			if(maxDate == null || sch.getCreationDate().after(maxDate)){
				maxDate = sch.getCreationDate();
				fs=sch;
			}
		}
		
		return fs;
	}

	@Override
	public Log save(Log l) {
		return logRepo.save(l);
	}
	
	@Override
	public List<Log> findAllLogs() {
		return logRepo.findAll();
	}

	@Override
	public Game findGameById(Integer id) {
		return gameRepo.findById(id);
	}

	@Override
	public void delete(Game schedule) {
		gameRepo.delete(schedule);
	}

	@Override
	public List<Game> findAllGames() {
		return gameRepo.findAll();
	}
}
