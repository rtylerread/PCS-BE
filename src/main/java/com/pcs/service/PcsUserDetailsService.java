package com.pcs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.pcs.model.UserDTO;
import com.pcs.repo.UserRepository;

public class PcsUserDetailsService implements UserDetailsService{

	
	@Autowired 
	UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		final UserDTO user = userRepository.getUser(email);
		if (user == null) {
            throw new UsernameNotFoundException(email);
        }
        UserDetails usr = User.withUsername(user.getEmail()).password(user.getPass()).authorities("USER").build();
        return usr;
	}

}
