package com.vasa.scheduling.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vasa.scheduling.domain.SportSchedulingRules;
import com.vasa.scheduling.repositiories.RulesRepository;

@Service("rulesService")
public class RulesServiceImpl implements RulesService {

	@Autowired
	private RulesRepository rulesRepo;
	
	@Override
	public List<SportSchedulingRules> findAll() {
		return rulesRepo.findAll();
	}

}
