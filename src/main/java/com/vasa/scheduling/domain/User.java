package com.vasa.scheduling.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.vasa.scheduling.enums.Carrier;
import com.vasa.scheduling.enums.Status;
import com.vasa.scheduling.enums.UserType;

@Entity
@Table(name="USER")
public class User {
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy="coach")
	private List<Team> team;
	
	@Id
	@SequenceGenerator(name = "UserSequence", sequenceName = "SEQ_USER_PK", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="UserSequence")
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
	
	private String phone;
	
	private Carrier carrier;
	
	@NotNull
	private Status status;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="SS")
	private Date lastLogin;
	
	private int loginFailures=0;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="SS")
	private Date memberSince;
	
	@NotNull
	private UserType userType;
	
	@NotNull
	private boolean skipNotifications=false;
	
	@NotNull
	private boolean skipTextNotifications=true;
	
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
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
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
		for(Team userTeam : team){
			if(userTeam.getSeason() != null && userTeam.getSeason().getStatus()==Status.ACTIVE){
				return userTeam;
			}
		}
		return null;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Carrier getCarrier() {
		return carrier;
	}
	public void setCarrier(Carrier carrier) {
		this.carrier = carrier;
	}
	public boolean isSkipNotifications() {
		return skipNotifications;
	}
	public void setSkipNotifications(boolean skipNotifications) {
		this.skipNotifications = skipNotifications;
	}
	public boolean isSkipTextNotifications() {
		return skipTextNotifications;
	}
	public void setSkipTextNotifications(boolean skipNotifications) {
		this.skipTextNotifications = skipNotifications;
	}
}
