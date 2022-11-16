package com.pcs.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@Entity
@Table(name="APPOINTMENTS")
public class AppointmentDTO {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer appointmentId;
	
	@Column(name="PROPERTY_ID", nullable = false, unique = false)
	private Integer propertyId;
	
	@Temporal(TemporalType.DATE)
	@Column(name="SCHEDULED_DT", nullable = false, unique = false)
	private Date scheduledDt;
	
	@Temporal(TemporalType.DATE)
	@Column(name="COMPLETED_DT", nullable = true, unique = false)
	private Date completedDt;
	
	@Column(name="APP_STATUS", nullable = true, unique = false)
	private String appStatus;
	
	public Integer getAppointmentId() {
		return appointmentId;
	}
	public void setAppointmentId(Integer appointmentId) {
		this.appointmentId = appointmentId;
	}
	public Integer getPropertyId() {
		return propertyId;
	}
	public void setPropertyId(Integer propertyId) {
		this.propertyId = propertyId;
	}
	public Date getScheduledDt() {
		return scheduledDt;
	}
	public void setScheduledDt(Date scheduledDt) {
		this.scheduledDt = scheduledDt;
	}
	public Date getCompletedDt() {
		return completedDt;
	}
	public void setCompletedDt(Date completedDt) {
		this.completedDt = completedDt;
	}
	public String getAppStatus() {
		return appStatus;
	}
	public void setAppStatus(String appStatus) {
		this.appStatus = appStatus;
	}
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
