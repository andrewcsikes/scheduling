package com.vasa.scheduling.services;

import java.util.List;

import com.vasa.scheduling.domain.Sport;

public interface SportService{

	List<Sport> findAll();
	Sport save(Sport s);
	Sport findById(Integer sportId);
	void delete(Sport sport);
}
