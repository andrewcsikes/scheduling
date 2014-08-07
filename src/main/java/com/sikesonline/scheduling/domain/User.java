package com.sikesonline.scheduling.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import com.sikesonline.scheduling.enums.UserStatus;

@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="MEMBER_SEQ")
	@SequenceGenerator(name="MEMBER_SEQ", sequenceName="MEMBER_SEQ", initialValue = 1, allocationSize = 1)
	private Integer id;

	@NotNull
	private String firstName;
	
	@NotNull
	private String lastName;
	
	@NotNull
	private String userName;
	
	@NotNull
	private String password;
	
	private String passwordReminder;
	
	@NotNull
	private String emailAddress;
	
	@NotNull
	private String address1;
	
	private String address2;
	
	@NotNull
	private String city;
	
	@NotNull
	private String state;
	
	@NotNull
	private String postalCode;
	
	@NotNull
	private UserStatus status;
	
	@NotNull
	private Date lastLogin;
	
	private int loginFailures=0;
	
	@NotNull
	private Date memberSince;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPassword() {
		return password;
	}
	public void setUserPassword(String userPassword) {
		this.password = userPassword;
	}
	public String getPasswordReminder() {
		return passwordReminder;
	}
	public void setPasswordReminder(String passwordReminder) {
		this.passwordReminder = passwordReminder;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public UserStatus getStatus() {
		return status;
	}
	public void setStatus(UserStatus status) {
		this.status = status;
	}
	public Date getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}
	public int getLoginFailures() {
		return loginFailures;
	}
	public void setLoginFailures(int loginFailures) {
		this.loginFailures = loginFailures;
	}
	public Date getMemberSince() {
		return memberSince;
	}
	public void setMemberSince(Date memberSince) {
		this.memberSince = memberSince;
	}
	
}
