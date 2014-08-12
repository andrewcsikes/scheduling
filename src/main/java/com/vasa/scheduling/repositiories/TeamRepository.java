package com.vasa.scheduling.repositiories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.vasa.scheduling.domain.Team;
import com.vasa.scheduling.domain.User;

@Repository
@Transactional(readOnly = true)
public interface TeamRepository extends JpaRepository<Team, Integer>{

	Team findByCoach(User user);
	
}