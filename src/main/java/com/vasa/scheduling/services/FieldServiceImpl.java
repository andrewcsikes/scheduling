package com.vasa.scheduling.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vasa.scheduling.domain.Fields;
import com.vasa.scheduling.repositiories.FieldRepository;

@Service("fieldService")
public class FieldServiceImpl implements FieldService {

	@Autowired
	private FieldRepository fieldRepo;
	
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

}
