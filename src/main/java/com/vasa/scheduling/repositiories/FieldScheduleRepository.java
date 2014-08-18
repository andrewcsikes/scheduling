package com.vasa.scheduling.repositiories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.vasa.scheduling.domain.FieldSchedule;

@Repository
@Transactional(readOnly = true)
public interface FieldScheduleRepository extends JpaRepository<FieldSchedule, Integer>{

	@Query("Select s from FieldSchedule s where DATE(date)=DATE(:d) and field.name=:field ")
	List<FieldSchedule> findByDateAndFieldName(@Param("d") Date d, @Param("field") String field);
	FieldSchedule findById(Integer id);
	
}