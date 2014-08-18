package com.vasa.scheduling.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vasa.scheduling.domain.FieldSchedule;
import com.vasa.scheduling.domain.Fields;
import com.vasa.scheduling.repositiories.FieldRepository;
import com.vasa.scheduling.repositiories.FieldScheduleRepository;

@Service("scheduleService")
public class ScheduleServiceImpl implements ScheduleService {

	@Autowired
	private FieldScheduleRepository repo;
	
	@Autowired
	private FieldRepository fieldRepo;
	
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
		return fieldRepo.findAll();
	}

}
