package com.vasa.scheduling.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vasa.scheduling.domain.FieldRule;
import com.vasa.scheduling.domain.Fields;
import com.vasa.scheduling.repositiories.FieldRepository;
import com.vasa.scheduling.repositiories.FieldRuleRepository;

@Service("fieldService")
public class FieldServiceImpl implements FieldService {

	@Autowired
	private FieldRepository fieldRepo;
	
	@Autowired
	private FieldRuleRepository fieldRuleRepo;
	
	@Override
	public List<Fields> findAll() {
		return fieldRepo.findAll();
	}

	@Override
	public Fields save(Fields s) {
		return fieldRepo.save(s);
	}

	@Override
	public Fields findById(Integer fieldId) {
		return fieldRepo.findById(fieldId);
	}

	@Override
	public void delete(Fields f) {
		fieldRepo.delete(f);		
	}

	@Override
	public FieldRule save(FieldRule rule) {
		return fieldRuleRepo.save(rule);
	}

	@Override
	public FieldRule findRuleById(Integer id) {
		return fieldRuleRepo.findById(id);
	}

	@Override
	public void delete(FieldRule rule) {
		fieldRuleRepo.delete(rule);
	}

}
