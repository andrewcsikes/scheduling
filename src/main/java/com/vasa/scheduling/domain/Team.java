package com.vasa.scheduling.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="TEAM")
public class Team {
	
	@Id
	@GeneratedValue
	private Integer id;

	@NotNull
	@OneToOne
	private Sport sport;
	
	@NotNull
	@OneToOne
	private AgeGroup ageGroup;
	
	@NotNull
	@OneToOne
	private Season season;
	
	@NotNull
	private String name;
	
	@NotNull
	@OneToOne
	private User coach;
	
	public Sport getSport() {
		return sport;
	}
	public void setSport(Sport sport) {
		this.sport = sport;
	}
	public AgeGroup getAgeGroup() {
		return ageGroup;
	}
	public void setAgeGroup(AgeGroup ageGroup) {
		this.ageGroup = ageGroup;
	}
	public Season getSeason() {
		return season;
	}
	public void setSeason(Season season) {
		this.season = season;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public User getCoach() {
		return coach;
	}
	public void setCoach(User coach) {
		this.coach = coach;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	

}
