package com.vasa.scheduling.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.vasa.scheduling.enums.DayOfWeek;

@Entity
@Table(name="SPORT")
public class Sport {

	@Id
	@SequenceGenerator(name = "SportSequence", sequenceName = "SEQ_SPORT_PK", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SportSequence")
	private Integer id;
	
	@NotNull
	private String name;
	
	private DayOfWeek dayOfWeek;
	
	private DayOfWeek nonVasaDayOfWeek;
	
	private Integer time;

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

	public DayOfWeek getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(DayOfWeek dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public DayOfWeek getNonVasaDayOfWeek() {
		return nonVasaDayOfWeek;
	}

	public void setNonVasaDayOfWeek(DayOfWeek nonVasaDayOfWeek) {
		this.nonVasaDayOfWeek = nonVasaDayOfWeek;
	}

	public Integer getTime() {
		return time;
	}

	public void setTime(Integer time) {
		this.time = time;
	}
	
	public String getFormattedTime(){
		if(getTime() != null){
			if(getTime() > 12){
				return getTime()-12 + " PM";
			}else if(getTime() == 12){
				return getTime() + " PM";
			}else{
				return getTime() + " AM";
			}
		}else{
			return null;
		}
	}
}
