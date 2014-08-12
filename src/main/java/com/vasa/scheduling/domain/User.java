package com.vasa.scheduling.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.vasa.scheduling.enums.UserStatus;
import com.vasa.scheduling.enums.UserType;
import com.vasa.scheduling.services.UserService;

@Entity
@Table(name="user")
public class User {

	@Autowired
	@Transient
	UserService us;
	
	@Id
	@GeneratedValue
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
	
	private String address1;
	
	private String address2;
	
	private String city;
	
	private String state;
	
	private String postalCode;
	
	@NotNull
	private UserStatus status;
	
	@NotNull
	private Date lastLogin;
	
	private int loginFailures=0;
	
	@NotNull
	private Date memberSince;
	
	@NotNull
	private UserType userType;
	
	
	public UserType getUserType() {
		return userType;
	}
	public void setUserType(UserType userType) {
		this.userType = userType;
	}
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String userPassword) {
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
	
	public Team getTeam(){
		return us.getTeam(this); 
	}

}
