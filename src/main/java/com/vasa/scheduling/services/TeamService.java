package com.vasa.scheduling.services;

import java.util.List;

import com.vasa.scheduling.domain.Sport;
import com.vasa.scheduling.domain.Team;

public interface TeamService{

	List<Team> findAll();
	Team save(Team member);
	Team findByName(String name);
	List<Team> findActive();
	Team findById(Integer teamId);
	List<Sport> findAllSports();

}
