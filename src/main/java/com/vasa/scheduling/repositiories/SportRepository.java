package com.vasa.scheduling.repositiories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.vasa.scheduling.domain.Sport;

@Repository
@Transactional(readOnly = true)
public interface SportRepository extends JpaRepository<Sport, Integer>{

	Sport findByName(String name);

	Sport findById(Integer id);

}