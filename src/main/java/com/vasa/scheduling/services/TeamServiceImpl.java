package com.vasa.scheduling.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import com.vasa.scheduling.domain.Sport;
import com.vasa.scheduling.domain.Team;
import com.vasa.scheduling.enums.Status;
import com.vasa.scheduling.repositiories.SportRepository;
import com.vasa.scheduling.repositiories.TeamRepository;

@Service("teamService")
public class TeamServiceImpl implements TeamService{
	
	@Autowired
	private TeamRepository teamRepo;
	
	@Autowired
	private SportRepository sportRepo;
	
	@Override
	public Team save(Team member) {
		return teamRepo.save(member);
	}
	
	@Override
	public Team findByName(String name) {
		return teamRepo.findByName(name);
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
	
}
