package com.vasa.scheduling.repositiories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.vasa.scheduling.domain.Game;
import com.vasa.scheduling.domain.Team;

@Repository
@Transactional(readOnly = true)
public interface GameRepository extends JpaRepository<Game, Integer>{

	@Query("Select s from Game s where DATE(date)=DATE(:d) and field.name=:field")
	List<Game> findByDayAndFieldName(@Param("d") Date d, @Param("field") String field);
	
	Game findById(Integer id);
	
	@Query("Select s from Game s where date=:d and field.name=:field")
	Game findByDateAndFieldName(@Param("d") Date d, @Param("field") String field);

	@Query("Select s from Game s where month(date)=month(:d) order by field, date, homeTeam")
	List<Game> findByMonth(@Param("d") Date date);
	
	@Query("Select s from Game s where DATE(date)=DATE(:d) order by field, homeTeam")
	List<Game> findByDay(@Param("d") Date d);

	@Query("Select s from Game s where week(date)=week(:d) order by date, field, homeTeam")
	List<Game> findByWeek(@Param("d") Date d);
	
	@Query("Select s from Game s where field.name=:field order by date, homeTeam")
	List<Game> findByFieldName(@Param("field") String field);

//	@Query("Select s from Game s where month(date)=month(:d) and (homeTeam=:team.name or awayTeam=:team.name) order by date")
//	List<Game> findByMonthAndTeam(@Param("d") Date date, @Param("team") Team team);
//
//	@Query("Select s from Game s where week(date)=week(:d) and (homeTeam=:team.name or awayTeam=:team.name) order by date")
//	List<Game> findScheduleForWeek(@Param("team")Team team, @Param("d")Date calendarDay);
//
//	@Query("Select s from Game s where day(date)=day(:d) and (homeTeam=:team.name or awayTeam=:team.name) order by date")
//	List<Game> findScheduleForDay(@Param("team")Team team, @Param("d")Date calendarDay);
	
}