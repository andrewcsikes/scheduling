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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="FIELD_SCHEDULE")
public class FieldSchedule {

	// TODO: Add Repeatable entries
	
	@Id
	@SequenceGenerator(name = "FieldScheduleSequence", sequenceName = "SEQ_FIELD_SCH_PK", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="FieldScheduleSequence")
	private Integer id;
	
	@NotNull
	@OneToOne(fetch = FetchType.EAGER)
	private Fields field;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="SS")
	private Date date;
	
	@OneToOne(fetch = FetchType.EAGER)
	private Team team;
	
	private boolean game = false;
	
	private String gameDescription;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="SS")
	private Date creationDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Fields getField() {
		return field;
	}

	public void setField(Fields field) {
		this.field = field;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	public boolean getGame(){
		return game;
	}
	
	public void setGame(boolean game){
		this.game=game;
	}

	public String getGameDescription() {
		return gameDescription;
	}

	public void setGameDescription(String gameDescription) {
		this.gameDescription = gameDescription;
	}
	
}
