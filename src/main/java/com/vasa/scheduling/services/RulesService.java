package com.vasa.scheduling.services;

import java.util.List;

import com.vasa.scheduling.domain.SportSchedulingRules;

public interface RulesService{

	List<SportSchedulingRules> findAll();
}
