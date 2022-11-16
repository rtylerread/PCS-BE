package com.pcs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pcs.model.AppointmentDTO;
import com.pcs.model.PropertyDTO;
import com.pcs.model.ScheduleDTO;
import com.pcs.model.UserDTO;
import com.pcs.repo.PropertyRepository;
import com.pcs.repo.UserRepository;
import com.pcs.util.SecurityUtil;

@RestController
@RequestMapping("android-client")
public class ClientController {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PropertyRepository propertyRepository;
	
	@PostMapping(value="/auth", params = {"user"})
	public UserDTO AuthUser( @RequestParam UserDTO user) {
		UserDTO usr = userRepository.authUser(user.getEmail(), SecurityUtil.secureHash(user.getPass()));
		System.out.println(usr.toString());
		return usr;
	}
	@PostMapping(value="/new-user", params = {"email","pass"})
	public boolean makeNewUser( @RequestParam String email, @RequestParam String pass) {
		try {
			userRepository.addUser(email, SecurityUtil.secureHash(pass));
			return true;
		} catch(Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
	@PostMapping(value="/prop", params = {"user"})
	public List<PropertyDTO> getProperty(@RequestParam UserDTO user) {
		return propertyRepository.getPropertiesByUserId(user.getUserId());
	}
	@PostMapping(value="/prop", params = {"appointment"})
	public PropertyDTO getProperty(@RequestParam AppointmentDTO appointment) {
		//TODO
		return null;
	}
	@PostMapping(value="/appt", params = {"property"})
	public List<AppointmentDTO> getAppointment(@RequestParam PropertyDTO property){
		//TODO
		return null;
	}
	@PostMapping(value="/future-appt", params = {"property"})
	public List<AppointmentDTO> getFutureAppointment(@RequestParam PropertyDTO property){
		//TODO
		return null;
	}
	@PostMapping(value="/past-appt", params = {"property"})
	public List<AppointmentDTO> getPastAppointment(@RequestParam PropertyDTO property){
		//TODO
		return null;
	}
	@PostMapping(value="/appt", params = {"user"})
	public List<AppointmentDTO> getAppointment(@RequestParam UserDTO user){
		//TODO
		return null;
	}
	@PostMapping(value="/future-appt", params = {"user"})
	public List<AppointmentDTO> getFutureAppointment(@RequestParam UserDTO user){
		//TODO
		return null;
	}
	@PostMapping(value="/past-appt", params = {"user"})
	public List<AppointmentDTO> getPastAppointment(@RequestParam UserDTO user){
		//TODO
		return null;
	}
	@PostMapping(value="/schedule", params = {"user"})
	public ScheduleDTO getSchedule(@RequestParam UserDTO user) {
		//TODO
		return null;
	}
	@PostMapping(value="/schedule", params = {"property"})
	public ScheduleDTO getSchedule(@RequestParam PropertyDTO property) {
		//TODO
		return null;
	}
	@GetMapping("/test")
	public UserDTO getAllUsers(){
		return userRepository.authUser("rtylerr@umich.edu","0be64ae89ddd24e225434de95d501711339baeee18f009ba9b4369af27d30d60");
	}
}
