package com.vasa.scheduling.services;

import java.util.Date;
import java.util.List;

import com.vasa.scheduling.domain.FieldSchedule;
import com.vasa.scheduling.domain.Fields;
import com.vasa.scheduling.domain.Game;
import com.vasa.scheduling.domain.Log;
import com.vasa.scheduling.domain.Season;
import com.vasa.scheduling.domain.Sport;
import com.vasa.scheduling.domain.Team;

public interface ScheduleService{

	FieldSchedule save(FieldSchedule schedule);
	void delete(FieldSchedule schedule);
	List<FieldSchedule> findByDayField(Date date, String field);
	FieldSchedule findByDateField(Date date, String field);
	FieldSchedule findById(Integer id);
	Fields findFieldByName(String field);
	List<Fields> findAllFields();
	List<Fields> findAllFields(Sport sport);
	List<Season> findActiveSeasons();
	Season findSeasonById(Integer id);
	Sport findSportByName(String string);
	List<FieldSchedule> findByMonth(Date date);
	List<FieldSchedule> findByMonthAndTeam(Date date, Team team);
	List<FieldSchedule> findScheduleForWeek(Team team, Date calendarDay);
	List<FieldSchedule> findScheduleForDay(Team team, Date calendarDay);
	Season findSeason(Sport sport);
	List<FieldSchedule> findByFilter(Team team, Fields field, String filterClass, Date time);
	List<Game> findGamesByDayField(Date date, String name);
	List<Game> findGameByMonth(Date date);
	List<Game> findGamesByFilter(Team team, Fields field, String filterClass, Date time);
	Game save(Game schedule);
	Log save(Log l);
	List<Log> findAllLogs();
	FieldSchedule getLastActive();
	List<Game> findAllGames();
	Game findGameById(Integer id);
	void delete(Game schedule);
}
