package com.vasa.scheduling.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.vasa.scheduling.enums.Classification;

@Entity
@Table(name="TEAM")
public class Team {
	
	@Id
	@SequenceGenerator(name = "TeamSequence", sequenceName = "SEQ_TEAM_PK", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TeamSequence")
	private Integer id;

	@NotNull
	@OneToOne(fetch = FetchType.EAGER)
	private Sport sport;
	
	@NotNull
	@OneToOne(fetch = FetchType.EAGER)
	private AgeGroup ageGroup;
	
	@NotNull
	@OneToOne(fetch = FetchType.EAGER)
	private Season season;
	
	@NotNull
	private String name;
	
	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	private User coach;
	
	@NotNull
	private Classification classification;
	
	private Integer practiceLimit;
	
	private Integer weeklyPracticeLimit;
	
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
	public Classification getClassification() {
		return classification;
	}
	public void setClassification(Classification classification) {
		this.classification = classification;
	}
	public Integer getPracticeLimit() {
		return practiceLimit;
	}
	public void setPracticeLimit(Integer practiceLimit) {
		this.practiceLimit = practiceLimit;
	}
	public Integer getWeeklyPracticeLimit() {
		return weeklyPracticeLimit;
	}
	public void setWeeklyPracticeLimit(Integer weeklyPracticeLimit) {
		this.weeklyPracticeLimit = weeklyPracticeLimit;
	}
}
