package com.vasa.scheduling.services;

import java.util.Date;
import java.util.List;

import com.vasa.scheduling.domain.FieldSchedule;
import com.vasa.scheduling.domain.Fields;
import com.vasa.scheduling.domain.Season;
import com.vasa.scheduling.domain.Sport;
import com.vasa.scheduling.domain.Team;
import com.vasa.scheduling.enums.Classification;

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
	List<FieldSchedule> findByMonthAndClassification(Date time,
			Classification enumFromCode);
	List<FieldSchedule> findByMonthAndTeamAndClassification(Date time, Team team, 
			Classification classification);
}
