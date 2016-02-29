package com.vasa.scheduling.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.vasa.scheduling.enums.Operation;

@Entity
@Table(name="FIELD_RULE")
public class FieldRule {

	@Id
	@SequenceGenerator(name = "FieldRuleSequence", sequenceName = "SEQ_F_RULE_PK", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="FieldRuleSequence")
	private Integer id;
	
	@ManyToOne (cascade = {CascadeType.PERSIST, CascadeType.MERGE} ) 
	@JoinColumn(name="FIELDS_ID")
	private Fields fields = null;
	
	@NotNull
	@OneToOne(fetch = FetchType.EAGER)
	private AgeGroup ageGroup;
	
	@NotNull
	private Operation operation;
	
	@NotNull
	private Integer time;
	
	private String message;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Fields getFields() {
		return fields;
	}

	public void setFields(Fields fields) {
		this.fields = fields;
	}

	public AgeGroup getAgeGroup() {
		return ageGroup;
	}

	public void setAgeGroup(AgeGroup ageGroup) {
		this.ageGroup = ageGroup;
	}

	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}

	public Integer getTime() {
		return time;
	}

	public void setTime(Integer time) {
		this.time = time;
	}
	
	public String getMessage(){
		return message;
	}
	
	public void setMessage(String m){
		message = m;
	}
	
}
