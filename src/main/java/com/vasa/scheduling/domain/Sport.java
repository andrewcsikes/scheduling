package com.vasa.scheduling.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="SPORT")
public class Sport {

	@Id
	@SequenceGenerator(name = "SportSequence", sequenceName = "SEQ_SPORT_PK", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SportSequence")
	private Integer id;
	
	@NotNull
	private String name;

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
	
	
}
