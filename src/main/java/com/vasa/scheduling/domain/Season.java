package com.vasa.scheduling.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
}
