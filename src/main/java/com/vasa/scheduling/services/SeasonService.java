package com.vasa.scheduling.services;

import java.util.List;

import com.vasa.scheduling.domain.Season;

public interface SeasonService{

	List<Season> findAll();
	Season save(Season s);
	Season findById(Integer id);
	void delete(Season s);
}
