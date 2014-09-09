package com.vasa.scheduling.repositiories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.vasa.scheduling.domain.FieldSchedule;
import com.vasa.scheduling.domain.Team;
import com.vasa.scheduling.enums.Classification;

@Repository
@Transactional(readOnly = true)
public interface FieldScheduleRepository extends JpaRepository<FieldSchedule, Integer>{

	@Query("Select s from FieldSchedule s where DATE(date)=DATE(:d) and field.name=:field")
	List<FieldSchedule> findByDayAndFieldName(@Param("d") Date d, @Param("field") String field);
	
	FieldSchedule findById(Integer id);
	
	@Query("Select s from FieldSchedule s where date=:d and field.name=:field ")
	FieldSchedule findByDateAndFieldName(@Param("d") Date d, @Param("field") String field);

	@Query("Select s from FieldSchedule s where month(date)=month(:d) order by field, date, team")
	List<FieldSchedule> findByMonth(@Param("d") Date date);

	@Query("Select s from FieldSchedule s where month(date)=month(:d) and (team=:team or team2=:team) order by date")
	List<FieldSchedule> findByMonthAndTeam(@Param("d") Date date, @Param("team") Team team);

	@Query("Select s from FieldSchedule s where week(date)=week(:d) and (team=:team or team2=:team) order by date")
	List<FieldSchedule> findScheduleForWeek(@Param("team")Team team, @Param("d")Date calendarDay);
	
}