package com.vasa.scheduling.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vasa.scheduling.domain.Season;
import com.vasa.scheduling.repositiories.SeasonRepository;

@Service("seasonService")
public class SeasonServiceImpl implements SeasonService {

	@Autowired
	private SeasonRepository seasonRepo;
	
	@Override
	public List<Season> findAll() {
		return seasonRepo.findAll();
	}

	@Override
	public Season save(Season s) {
		return seasonRepo.save(s);
	}

	@Override
	public Season findById(Integer id) {
		return seasonRepo.findById(id);
	}

	@Override
	public void delete(Season s) {
		seasonRepo.delete(s);		
	}

}
