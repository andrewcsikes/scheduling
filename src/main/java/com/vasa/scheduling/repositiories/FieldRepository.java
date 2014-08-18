package com.vasa.scheduling.repositiories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.vasa.scheduling.domain.Fields;

@Repository
@Transactional(readOnly = true)
public interface FieldRepository extends JpaRepository<Fields, Integer>{

	Fields findByName(String name);
	
}