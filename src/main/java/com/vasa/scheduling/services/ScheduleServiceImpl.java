package com.vasa.scheduling.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vasa.scheduling.domain.FieldSchedule;
import com.vasa.scheduling.repositiories.FieldScheduleRepository;

@Service("scheduleService")
public class ScheduleServiceImpl implements ScheduleService {

	@Autowired
	private FieldScheduleRepository repo;
	
	@Override
	public FieldSchedule save(FieldSchedule schedule) {
		return repo.save(schedule);
	}

	@Override
	public List<FieldSchedule> findByDateField(Date date, String field) {
		return repo.findByDateAndFieldName(date, field);
	}

	@Override
	public FieldSchedule findById(Integer id) {
		return repo.findById(id);
	}

}
