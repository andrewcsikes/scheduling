package com.vasa.scheduling.repositiories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.vasa.scheduling.domain.SportSchedulingRules;

@Repository
@Transactional(readOnly = true)
public interface RulesRepository extends JpaRepository<SportSchedulingRules, Integer>{

}