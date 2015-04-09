package com.vasa.scheduling.repositiories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.vasa.scheduling.domain.Log;


@Repository
@Transactional(readOnly = true)
public interface LogRepository extends JpaRepository<Log, Integer>{
	
}