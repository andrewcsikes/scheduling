package com.vasa.scheduling.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import com.vasa.scheduling.domain.AgeGroup;
import com.vasa.scheduling.domain.Season;
import com.vasa.scheduling.domain.Sport;
import com.vasa.scheduling.domain.Team;
import com.vasa.scheduling.enums.Status;
import com.vasa.scheduling.repositiories.AgeGroupRepository;
import com.vasa.scheduling.repositiories.SportRepository;
import com.vasa.scheduling.repositiories.TeamRepository;

@Service("teamService")
public class TeamServiceImpl implements TeamService{
	
	@Autowired
	private TeamRepository teamRepo;
	
	@Autowired
	private SportRepository sportRepo;
	
	@Autowired
	private AgeGroupRepository ageRepo;
	
	@Override
	public Team save(Team member) {
		return teamRepo.save(member);
	}
	
	@Override
	public Team findByNameAndAgeGroup(String name, AgeGroup ag) {
		return teamRepo.findByNameAndAgeGroup(name, ag);
	}

	@Override
	public List<Team> findAll() {
		return teamRepo.findAll();
	}
	
	@Override
	public List<Team> findActive() {
		return teamRepo.findBySeasonStatus(Status.ACTIVE);
	}
	
	@Override
	public Team findById(Integer teamId){
		return teamRepo.findById(teamId);
	}

	@Override
	public List<Sport> findAllSports() {
		return sportRepo.findAll();
	}

	@Override
	public List<AgeGroup> findAllAgegroups() {
		return ageRepo.findAll();
	}

	@Override
	public Sport findSportById(Integer id) {
		return sportRepo.findById(id);
	}

	@Override
	public AgeGroup findAgeGroupById(Integer id) {
		return  ageRepo.findById(id);
	}
	
	@Override
	public AgeGroup findAgeGroupByName(String name) {
		return  ageRepo.findByName(name);
	}
	
}
