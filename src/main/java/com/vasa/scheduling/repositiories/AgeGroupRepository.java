package com.vasa.scheduling.repositiories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.vasa.scheduling.domain.AgeGroup;

@Repository
@Transactional(readOnly = true)
public interface AgeGroupRepository extends JpaRepository<AgeGroup, Integer>{

	AgeGroup findById(Integer id);
	AgeGroup findByName(String name);

}