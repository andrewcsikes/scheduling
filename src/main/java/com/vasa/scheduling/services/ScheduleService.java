package com.vasa.scheduling.services;

import java.util.Date;
import java.util.List;

import com.vasa.scheduling.domain.FieldSchedule;

public interface ScheduleService{

	FieldSchedule save(FieldSchedule schedule);
	List<FieldSchedule> findByDateField(Date date, String field);
	FieldSchedule findById(Integer id);

}
