package com.vasa.scheduling.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.vasa.scheduling.enums.SeasonStatus;

@Entity
@Table(name="SEASON")
public class Season {

	@Id
	@GeneratedValue
	private Integer id;
	
	@NotNull
	private String name;
	
	@NotNull
	private SeasonStatus status;

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

	public SeasonStatus getStatus() {
		return status;
	}

	public void setStatus(SeasonStatus status) {
		this.status = status;
	}
}
