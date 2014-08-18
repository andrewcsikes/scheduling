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

import com.vasa.scheduling.enums.Status;

@Entity
@Table(name="FIELDS")
public class Fields {

	@Id
	@SequenceGenerator(name = "FieldSequence", sequenceName = "SEQ_FIELD_PK", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="FieldSequence")
	private Integer id;
	
	@NotNull
	private String name;
	
	@NotNull
	@OneToOne(fetch = FetchType.LAZY)
	private Sport sport;
	
	@NotNull
	private Status status;
	
	@NotNull
	private boolean lights;
	
	@NotNull
	private String location;

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

	public Sport getSport() {
		return sport;
	}

	public void setSport(Sport sport) {
		this.sport = sport;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public boolean isLights() {
		return lights;
	}

	public void setLights(boolean lights) {
		this.lights = lights;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
}