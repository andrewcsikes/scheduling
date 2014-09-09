package com.vasa.scheduling.services;

import java.util.List;

import com.vasa.scheduling.domain.AgeGroup;
import com.vasa.scheduling.domain.Season;
import com.vasa.scheduling.domain.Sport;
import com.vasa.scheduling.domain.Team;

public interface TeamService{

	List<Team> findAll();
	Team save(Team member);
	Team findByNameAndAgeGroup(String name, AgeGroup ag);
	List<Team> findActive();
	Team findById(Integer teamId);
	List<Sport> findAllSports();
	List<AgeGroup> findAllAgegroups();
	Sport findSportById(Integer id);
	AgeGroup findAgeGroupById(Integer id);
	AgeGroup findAgeGroupByName(String name);

}
