package com.vasa.scheduling.repositiories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.vasa.scheduling.domain.Fields;
import com.vasa.scheduling.domain.Sport;
import com.vasa.scheduling.enums.Status;

@Repository
@Transactional(readOnly = true)
public interface FieldRepository extends JpaRepository<Fields, Integer>{

	Fields findByName(String name);
	List<Fields> findBySportOrderByIdAsc(Sport sport);
	List<Fields> findByStatusOrderByIdAsc(Status status);
}