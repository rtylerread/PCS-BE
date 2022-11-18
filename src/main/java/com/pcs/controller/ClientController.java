package com.pcs.controller;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.GenericJDBCException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.pcs.model.AppointmentDTO;
import com.pcs.model.PropertyDTO;
import com.pcs.model.ScheduleDTO;
import com.pcs.model.UserDTO;
import com.pcs.repo.AppointmentRepository;
import com.pcs.repo.PropertyRepository;
import com.pcs.repo.UserRepository;
import com.pcs.util.SecurityUtil;

import lombok.extern.log4j.Log4j2;




@RestController
@Log4j2
public class ClientController {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PropertyRepository propertyRepository;
	
	@Autowired
	AppointmentRepository appointmentRepository;
	
	@PostMapping(value="/auth")
	public ResponseEntity<UserDTO> AuthUser( @RequestBody UserDTO user) throws InvalidKeyException, IllegalArgumentException, JWTCreationException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException {
		UserDTO usr = userRepository.authUser(user.getEmail(), SecurityUtil.secureHash(user.getPass()));
//		System.out.println(user.toString());
//		System.out.println(SecurityUtil.secureHash(user.getPass()));
//		System.out.println(usr.toString());
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth", SecurityUtil.genAuthHeader(usr));
		return new ResponseEntity<UserDTO>(usr,headers,HttpStatus.OK);
	}
	@PostMapping(value="/new-user")
	public ResponseEntity<String> makeNewUser( @RequestBody UserDTO user) throws InvalidKeyException, IllegalArgumentException, JWTCreationException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException {
		String responseMsg="";
		UserDTO usr=null;
		try {
			usr = userRepository.addUser(user.getEmail(), SecurityUtil.secureHash(user.getPass()));
			responseMsg= "SUCCESS";
		} catch(Exception e) {
			if(e.getClass() == org.springframework.orm.jpa.JpaSystemException.class) {
				JpaSystemException ex = (JpaSystemException) e;
				if(ex.getLocalizedMessage().equalsIgnoreCase("could not execute query; nested exception is org.hibernate.exception.GenericJDBCException: could not execute query")) {
					responseMsg= "SUCCESS";
				}
			} else if( e.getClass() == DataIntegrityViolationException.class) {
				responseMsg= "EXISTS";
			}else {
				responseMsg= "FAILED";
			}
		}
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth", SecurityUtil.genAuthHeader(usr));
		return new ResponseEntity<String>(responseMsg,headers,HttpStatus.OK);
	}
	@PostMapping(value="/android-client/prop")
	public List<PropertyDTO> getProperty(@RequestBody UserDTO user) throws InvalidKeyException, IllegalArgumentException, JWTCreationException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException {
		System.out.println("here!");
		return propertyRepository.getPropertiesByUserId(user.getUserId());
		
	}
	@PostMapping(value="/android-client/appt", params = {"property"})
	public List<AppointmentDTO> getAppointment(@RequestParam PropertyDTO property){
		return appointmentRepository.getAllAppointmentsByPropertyId(property.getPropertyId());
	}
	@PostMapping(value="/android-client/future-appt", params = {"property"})
	public List<AppointmentDTO> getFutureAppointment(@RequestParam PropertyDTO property){
		return appointmentRepository.getFutureAppointmentsByPropertyId(property.getPropertyId());
	}
	@PostMapping(value="/android-client/past-appt", params = {"property"})
	public List<AppointmentDTO> getPastAppointment(@RequestParam PropertyDTO property){
		return appointmentRepository.getPastAppointmentsByPropertyId(property.getPropertyId());
	}
	@PostMapping(value="/android-client/appt", params = {"user"})
	public List<AppointmentDTO> getAppointment(@RequestParam UserDTO user){
		return appointmentRepository.getAllAppointmentsByUserId(user.getUserId());
	}
	@PostMapping(value="/android-client/future-appt", params = {"user"})
	public List<AppointmentDTO> getFutureAppointment(@RequestParam UserDTO user){
		return appointmentRepository.getFutureAppointmentsByUserId(user.getUserId()); 
	}
	@PostMapping(value="/android-client/past-appt", params = {"user"})
	public List<AppointmentDTO> getPastAppointment(@RequestParam UserDTO user){
		return appointmentRepository.getPastAppointmentsByUserId(user.getUserId());
	}
	@PostMapping(value="/android-client/schedule", params = {"user"})
	public ScheduleDTO getSchedule(@RequestParam UserDTO user) {
		//TODO
		return null;
	}
	@GetMapping("/test")
	public UserDTO getAllUsers(){
		 return userRepository.authUser("rtylerr@umich.edu","0be64ae89ddd24e225434de95d501711339baeee18f009ba9b4369af27d30d60");
	}
}
