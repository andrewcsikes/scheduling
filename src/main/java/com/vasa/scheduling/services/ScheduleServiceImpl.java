package com.vasa.scheduling.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vasa.scheduling.domain.FieldSchedule;
import com.vasa.scheduling.domain.Fields;
import com.vasa.scheduling.domain.Season;
import com.vasa.scheduling.domain.Sport;
import com.vasa.scheduling.domain.Team;
import com.vasa.scheduling.enums.Status;
import com.vasa.scheduling.repositiories.FieldRepository;
import com.vasa.scheduling.repositiories.FieldScheduleRepository;
import com.vasa.scheduling.repositiories.SeasonRepository;
import com.vasa.scheduling.repositiories.SportRepository;

@Service("scheduleService")
public class ScheduleServiceImpl implements ScheduleService {

	@Autowired
	private FieldScheduleRepository repo;
	
	@Autowired
	private FieldRepository fieldRepo;
	
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
		return fieldRepo.findAll();
	}
	
	@Override
	public List<Fields> findAllFields(Sport sport) {
		return fieldRepo.findBySport(sport);
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
}
