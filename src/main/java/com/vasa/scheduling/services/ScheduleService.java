package com.vasa.scheduling.services;

import java.util.Date;
import java.util.List;

import com.vasa.scheduling.domain.FieldSchedule;
import com.vasa.scheduling.domain.Fields;

public interface ScheduleService{

	FieldSchedule save(FieldSchedule schedule);
	void delete(FieldSchedule schedule);
	List<FieldSchedule> findByDayField(Date date, String field);
	FieldSchedule findByDateField(Date date, String field);
	FieldSchedule findById(Integer id);
	Fields findFieldByName(String field);
	List<Fields> findAllFields();

}
