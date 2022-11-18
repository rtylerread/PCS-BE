package com.pcs.repo;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pcs.model.UserDTO;

@Repository
public interface UserRepository extends JpaRepository<UserDTO, Integer> {

	@Query(nativeQuery=true, value="call AUTH_USER(:EM, :PASSHASH)")
	UserDTO authUser(String EM, String PASSHASH);
	
	@Query(nativeQuery=true, value="call ADD_USER(:I_EMAIL, :I_PASS)")
	UserDTO addUser(String I_EMAIL, String I_PASS);
	
	@Query(nativeQuery=true, value="call GET_USER(:EM)")
	UserDTO getUser(String EM);
}
