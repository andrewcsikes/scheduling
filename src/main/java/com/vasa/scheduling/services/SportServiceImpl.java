package com.vasa.scheduling.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vasa.scheduling.domain.Sport;
import com.vasa.scheduling.repositiories.SportRepository;

@Service("sportService")
public class SportServiceImpl implements SportService {

	@Autowired
	private SportRepository sportRepo;
	
	@Override
	public List<Sport> findAll() {
		return sportRepo.findAll();
	}

	@Override
	public Sport save(Sport s) {
		return sportRepo.save(s);
	}

	@Override
	public Sport findById(Integer sportId) {
		return sportRepo.findById(sportId);
	}

	@Override
	public void delete(Sport sport) {
		sportRepo.delete(sport);		
	}

}
