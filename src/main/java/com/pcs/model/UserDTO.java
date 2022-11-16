package com.pcs.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@Entity
@Table(name="USER")
public class UserDTO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "USER_ID", nullable = false, unique = false)
	private Integer userId;
	
	@Column(name = "EMAIL", length = 500, nullable = false, unique = true)
	private String email;
	
	@Column(name = "PASS", length = 1000, nullable = false, unique = false)
	private String pass;
	
	@Column(name = "PAYMENT_STATUS", nullable = true, unique = false)
	private String paymentStatus;
	
	@Column(name = "ACTIVE", nullable = true, unique = false)
	private String active;
	
	public UserDTO(Integer userId, String email, String pass) {
		this.userId = userId;
		this.email = email;
		this.pass = pass;
	}
	
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}
