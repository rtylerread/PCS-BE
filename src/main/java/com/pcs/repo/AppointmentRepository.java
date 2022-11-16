package com.pcs.repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pcs.model.AppointmentDTO;
import com.pcs.model.PropertyDTO;
import com.pcs.model.UserDTO;

import org.springframework.data.jpa.repository.query.Procedure;

@Repository
public interface AppointmentRepository extends JpaRepository<AppointmentDTO, Integer> {
	
	@Procedure("GET_PAST_APPOINTMENTS_BY_USER_ID")
	List<AppointmentDTO> getPastAppointmentsByUserId( int userId);
	
	@Procedure("GET_ALL_APPOINTMENTS_BY_USER_ID")
	List<AppointmentDTO> getAllAppointmentsByUserId( int userId);
	
	@Procedure("GET_PAST_APPOINTMENTS_BY_PROPERTY_ID")
	List<AppointmentDTO> getPastAppointmentsByPropertyId( int userId);
	
	@Procedure("GET_FUTURE_APPOINTMENTS_BY_PROPERTY_ID")
	List<AppointmentDTO> getFutureAppointmentsByPropertyId( int userId);
	
	@Procedure("GET_ALL_APPOINTMENTS_BY_PROPERTY_ID")
	List<AppointmentDTO> getAllAppointmentsByPropertyId( int userId);
	
	@Procedure("ADD_APPOINTMENT")
	void addAppointment( Integer propertyId, Date scheduledDt, Date completedDt, String appStatus );
	
}
