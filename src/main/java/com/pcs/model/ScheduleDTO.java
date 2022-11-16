package com.pcs.model;

import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class ScheduleDTO {
	
	private List<AppointmentDTO> pastAppointments;
	private List<AppointmentDTO> futureAppointments;
	
	public List<AppointmentDTO> getPastAppointments() {
		return pastAppointments;
	}
	public void setPastAppointments(List<AppointmentDTO> pastAppointments) {
		this.pastAppointments = pastAppointments;
	}
	public List<AppointmentDTO> getFutureAppointments() {
		return futureAppointments;
	}
	public void setFutureAppointments(List<AppointmentDTO> futureAppointments) {
		this.futureAppointments = futureAppointments;
	}
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
