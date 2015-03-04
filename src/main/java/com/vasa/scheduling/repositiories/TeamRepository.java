package com.vasa.scheduling.repositiories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.vasa.scheduling.domain.AgeGroup;
import com.vasa.scheduling.domain.Sport;
import com.vasa.scheduling.domain.Team;
import com.vasa.scheduling.domain.User;
import com.vasa.scheduling.enums.Status;
import com.vasa.scheduling.enums.UserType;

@Repository
@Transactional(readOnly = true)
public interface TeamRepository extends JpaRepository<Team, Integer>{

	Team findByCoach(User user);
	Team findByNameAndAgeGroup(String name, AgeGroup ag);
	List<Team> findBySeasonStatusOrderByAgeGroupNameAscNameAsc(Status status);
	Team findById(Integer teamId);
	List<Team> findBySportAndSeasonStatusOrderByAgeGroupNameAscNameAsc(Sport sport, Status status);
	
	@Query("Select u from User u where userType=:type ")
	List<User> findByUserType(@Param("type")UserType type);
	
}