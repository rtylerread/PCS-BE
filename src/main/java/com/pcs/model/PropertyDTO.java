package com.pcs.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@Entity
@Table(name="PROPERTY")
public class PropertyDTO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="PROPERTY_ID", nullable=false, unique=true)
	private Integer propertyId;
	
	@Column(name="USER_ID", nullable=false, unique=false)
	private Integer userId;
	
	@Column(name="P_NAME", nullable=false, unique=false)
	private String name;
	
	@Column(name="STORIES", nullable=false, unique=false)
	private Integer stories;
	
	@Column(name="NOTES", length=2000, nullable=true, unique=false)
	private String notes;
	
	@Column(name="ADDRESS", length=500, nullable=true, unique=false)
	private String address;
	
	@Column(name="LAT", nullable=true, unique=false)
	private Long latitude;
	
	@Column(name="LON", nullable=true, unique=false)
	private Long longitude;
	
	@Column(name="COM_RES", nullable=false, unique=false)
	private String comRes;
	
	public Integer getPropertyId() {
		return propertyId;
	}
	public void setPropertyId(Integer propertyId) {
		this.propertyId = propertyId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getStories() {
		return stories;
	}
	public void setStories(Integer stories) {
		this.stories = stories;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Long getLatitude() {
		return latitude;
	}
	public void setLatitude(Long latitude) {
		this.latitude = latitude;
	}
	public Long getLongitude() {
		return longitude;
	}
	public void setLongitude(Long longitude) {
		this.longitude = longitude;
	}
	public String getComRes() {
		return comRes;
	}
	public void setComRes(String comRes) {
		this.comRes = comRes;
	}
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}
