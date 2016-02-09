package com.vasa.scheduling.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="IMPORTANT_DATES")
public class ImportantDates {

	@Id
	@SequenceGenerator(name = "ImpDatesSequence", sequenceName = "SEQ_IMP_DATES_PK", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ImpDatesSequence")
	private Integer id;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="SS")
	private Date date;
	
	@NotNull
	private String message;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
