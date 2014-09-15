package com.vasa.scheduling.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.vasa.scheduling.enums.Status;

@Entity
@Table(name="SEASON")
public class Season {

	@Id
	@SequenceGenerator(name = "SeasonSequence", sequenceName = "SEQ_SEASON_PK", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SeasonSequence")
	private Integer id;
	
	@NotNull
	private String name;
	
	@NotNull
	private Status status;
	
	@NotNull
	@OneToOne(fetch = FetchType.EAGER)
	private Sport sport;
	
	private Date startDate;
	
	@NotNull
	private boolean applySchedulingRules=false;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Sport getSport() {
		return sport;
	}

	public void setSport(Sport sport) {
		this.sport = sport;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public boolean isApplySchedulingRules() {
		return applySchedulingRules;
	}

	public void setApplySchedulingRules(boolean applySchedulingRules) {
		this.applySchedulingRules = applySchedulingRules;
	}
}
