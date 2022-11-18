package com.pcs.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import com.pcs.model.PropertyDTO;

@Repository
public interface PropertyRepository extends JpaRepository<PropertyDTO, Integer> {

	@Query(nativeQuery=true, value="call GET_PROPERTIES_BY_USER_ID(:U_ID)")
	List<PropertyDTO> getPropertiesByUserId( int U_ID);
	
	@Query(nativeQuery=true, value="call GET_PROPERTIES_BY_EMAIL(:EM)")
	List<PropertyDTO> getPropertiesByEmail( String EM);
	
	@Query(nativeQuery=true, value="call GET_PROPERTY_BY_APPT_ID(:APPT_ID)")
	List<PropertyDTO> getPropertiesByAppointmentId( int APPT_ID);
	
	@Query(nativeQuery=true, value="call ADD_PROPERTY(:I_USER_ID, :I_P_NAME, :I_SQFT, :I_TYPE, :I_STORIES, :I_NOTES, :I_ADDRESS, :I_LAT, :I_LON, :I_COM_RES)")
	PropertyDTO addProperty(int I_USER_ID, String I_P_NAME, int I_SQFT, String I_TYPE, int I_STORIES, String I_NOTES, String I_ADDRESS, int I_LAT, int I_LON, String I_COM_RES);
	
}
