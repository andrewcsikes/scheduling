package com.vasa.scheduling.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.vasa.scheduling.enums.Classification;
import com.vasa.scheduling.enums.DayOfWeek;
import com.vasa.scheduling.enums.Operation;

@Entity
@Table(name="RULES")
public class SportSchedulingRules {
	
	@Id
	@SequenceGenerator(name = "RulesSequence", sequenceName = "SEQ_RULES_PK", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="RulesSequence")
	private Integer id;
	
	@OneToOne(fetch = FetchType.LAZY)
	private Sport sport;
	
	@OneToOne(fetch = FetchType.LAZY)
	private AgeGroup ageGroup;
	
	@OneToOne(fetch = FetchType.LAZY)
	private Fields field;
	
	private Classification teamClassification;
	
	@NotNull
	private Operation operation;
	
	private DayOfWeek dayOfWeek;
	
	private String hour;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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

	public Fields getField() {
		return field;
	}

	public void setField(Fields field) {
		this.field = field;
	}

	public Classification getTeamClassification() {
		return teamClassification;
	}

	public void setTeamClassification(Classification teamClassification) {
		this.teamClassification = teamClassification;
	}

	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}

	public DayOfWeek getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(DayOfWeek dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}
	
}
