package com.pcs.repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.pcs.model.AppointmentDTO;
import com.pcs.model.PropertyDTO;
import com.pcs.model.UserDTO;

import org.springframework.data.jpa.repository.query.Procedure;

@Repository
public interface AppointmentRepository extends JpaRepository<AppointmentDTO, Integer> {
	
	@Query(nativeQuery=true, value="call GET_PAST_APPOINTMENTS_BY_USER_ID(:U_ID)")
	List<AppointmentDTO> getPastAppointmentsByUserId( int U_ID);
	
	@Query(nativeQuery=true, value="call GET_FUTURE_APPOINTMENTS_BY_USER_ID(:U_ID)")
	List<AppointmentDTO> getFutureAppointmentsByUserId( int U_ID);
	
	@Query(nativeQuery=true, value="call GET_ALL_APPOINTMENTS_BY_USER_ID(:U_ID)")
	List<AppointmentDTO> getAllAppointmentsByUserId( int U_ID);
	
	@Query(nativeQuery=true, value="call GET_PAST_APPOINTMENTS_BY_PROPERTY_ID(:P_ID)")
	List<AppointmentDTO> getPastAppointmentsByPropertyId( int P_ID);
	
	@Query(nativeQuery=true, value="call GET_FUTURE_APPOINTMENTS_BY_PROPERTY_ID(:P_ID)")
	List<AppointmentDTO> getFutureAppointmentsByPropertyId( int P_ID);
	
	@Query(nativeQuery=true, value="call GET_ALL_APPOINTMENTS_BY_PROPERTY_ID(:P_ID)")
	List<AppointmentDTO> getAllAppointmentsByPropertyId( int P_ID);
	
	@Query(nativeQuery=true, value="call ADD_APPOINTMENT(:I_PROPERTY_ID,:I_SCHEDULED_DT,:I_COMPLETED_DT, :I_APP_STATUS)")
	void addAppointment( Integer I_PROPERTY_ID, Date I_SCHEDULED_DT, Date I_COMPLETED_DT, String I_APP_STATUS );

}
