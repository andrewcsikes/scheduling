package com.vasa.scheduling.services;

import java.util.List;

import com.vasa.scheduling.domain.Fields;

public interface FieldService{

	List<Fields> findAll();
	Fields save(Fields f);
	Fields findById(Integer fieldId);
	void delete(Fields field);
}
