package com.vasa.scheduling.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
	
	private boolean availableSunday=true;
	
	private Integer sundayStartTime;
	
	private Integer sundayEndTime;
	
	private boolean availableMonday=true;
	
	private Integer mondayStartTime;
	
	private Integer mondayEndTime;
	
	private boolean availableTuesday=true;
	
	private Integer tuesdayStartTime;
	
	private Integer tuesdayEndTime;
	
	private boolean availableWednesday=true;
	
	private Integer wednesdayStartTime;
	
	private Integer wednesdayEndTime;
	
	private boolean availableThursday=true;
	
	private Integer thursdayStartTime;
	
	private Integer thursdayEndTime;
	
	private boolean availableFriday=true;
	
	private Integer fridayStartTime;
	
	private Integer fridayEndTime;
	
	private boolean availableSaturday=true;
	
	private Integer saturdayStartTime;
	
	private Integer saturdayEndTime;
	
	@OneToMany( cascade=CascadeType.ALL,  mappedBy="fields"  ,fetch=FetchType.LAZY ,orphanRemoval=true )
	private List<FieldRule> fieldRules = null;

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

	public boolean isAvailableSunday() {
		return availableSunday;
	}

	public void setAvailableSunday(boolean availableSunday) {
		this.availableSunday = availableSunday;
	}

	public Integer getSundayStartTime() {
		return sundayStartTime;
	}

	public void setSundayStartTime(Integer sundayStartTime) {
		this.sundayStartTime = sundayStartTime;
	}

	public Integer getSundayEndTime() {
		return sundayEndTime;
	}

	public void setSundayEndTime(Integer sundayEndTime) {
		this.sundayEndTime = sundayEndTime;
	}

	public boolean isAvailableMonday() {
		return availableMonday;
	}

	public void setAvailableMonday(boolean availableMonday) {
		this.availableMonday = availableMonday;
	}

	public Integer getMondayStartTime() {
		return mondayStartTime;
	}

	public void setMondayStartTime(Integer mondayStartTime) {
		this.mondayStartTime = mondayStartTime;
	}

	public Integer getMondayEndTime() {
		return mondayEndTime;
	}

	public void setMondayEndTime(Integer mondayEndTime) {
		this.mondayEndTime = mondayEndTime;
	}

	public boolean isAvailableTuesday() {
		return availableTuesday;
	}

	public void setAvailableTuesday(boolean availableTuesday) {
		this.availableTuesday = availableTuesday;
	}

	public Integer getTuesdayStartTime() {
		return tuesdayStartTime;
	}

	public void setTuesdayStartTime(Integer tuesdayStartTime) {
		this.tuesdayStartTime = tuesdayStartTime;
	}

	public Integer getTuesdayEndTime() {
		return tuesdayEndTime;
	}

	public void setTuesdayEndTime(Integer tuesdayEndTime) {
		this.tuesdayEndTime = tuesdayEndTime;
	}

	public boolean isAvailableWednesday() {
		return availableWednesday;
	}

	public void setAvailableWednesday(boolean availableWednesday) {
		this.availableWednesday = availableWednesday;
	}

	public Integer getWednesdayStartTime() {
		return wednesdayStartTime;
	}

	public void setWednesdayStartTime(Integer wednesdayStartTime) {
		this.wednesdayStartTime = wednesdayStartTime;
	}

	public Integer getWednesdayEndTime() {
		return wednesdayEndTime;
	}

	public void setWednesdayEndTime(Integer wednesdayEndTime) {
		this.wednesdayEndTime = wednesdayEndTime;
	}

	public boolean isAvailableThursday() {
		return availableThursday;
	}

	public void setAvailableThursday(boolean availableThursday) {
		this.availableThursday = availableThursday;
	}

	public Integer getThursdayStartTime() {
		return thursdayStartTime;
	}

	public void setThursdayStartTime(Integer thursdayStartTime) {
		this.thursdayStartTime = thursdayStartTime;
	}

	public Integer getThursdayEndTime() {
		return thursdayEndTime;
	}

	public void setThursdayEndTime(Integer thursdayEndTime) {
		this.thursdayEndTime = thursdayEndTime;
	}

	public boolean isAvailableFriday() {
		return availableFriday;
	}

	public void setAvailableFriday(boolean availableFriday) {
		this.availableFriday = availableFriday;
	}

	public Integer getFridayStartTime() {
		return fridayStartTime;
	}

	public void setFridayStartTime(Integer fridayStartTime) {
		this.fridayStartTime = fridayStartTime;
	}

	public Integer getFridayEndTime() {
		return fridayEndTime;
	}

	public void setFridayEndTime(Integer fridayEndTime) {
		this.fridayEndTime = fridayEndTime;
	}

	public boolean isAvailableSaturday() {
		return availableSaturday;
	}

	public void setAvailableSaturday(boolean availableSaturday) {
		this.availableSaturday = availableSaturday;
	}

	public Integer getSaturdayStartTime() {
		return saturdayStartTime;
	}

	public void setSaturdayStartTime(Integer saturdayStartTime) {
		this.saturdayStartTime = saturdayStartTime;
	}

	public Integer getSaturdayEndTime() {
		return saturdayEndTime;
	}

	public void setSaturdayEndTime(Integer saturdayEndTime) {
		this.saturdayEndTime = saturdayEndTime;
	}
	
	public List<FieldRule> getFieldRules(){
		return fieldRules;
	}
	
	public void addToRules(FieldRule rule){
		if(getFieldRules() == null){
			fieldRules = new ArrayList<FieldRule>();
		}
		if(rule.getFields()==null){
			rule.setFields(this);
		}
		fieldRules.add(rule);
	}

	public void removeRule(FieldRule rule) {
		fieldRules.remove(rule);
	}
}
