package com.pcs.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import com.pcs.model.PropertyDTO;

@Repository
public interface PropertyRepository extends JpaRepository<PropertyDTO, Integer> {

	@Procedure("GET_PROPERTIES_BY_USER_ID")
	List<PropertyDTO> getPropertiesByUserId( int userId);
	
	@Procedure("GET_PROPERTIES_BY_EMAIL")
	List<PropertyDTO> getPropertiesByEmail( int userId);
	
	@Procedure("GET_PROPERTY_BY_APPT_ID")
	List<PropertyDTO> getPropertiesByAppointmentId( int apptId);
	
	@Procedure("ADD_PROPERTY")
	void addProperty(int userId, String propertyName, int sqft, String type, int stories, String notes, String address, int lat, int lon, String comRes);
	
}
