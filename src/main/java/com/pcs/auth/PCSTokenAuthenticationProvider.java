package com.pcs.auth;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import com.pcs.model.UserDTO;
import com.pcs.repo.UserRepository;
import com.pcs.util.SecurityUtil;


public class PCSTokenAuthenticationProvider implements AuthenticationProvider {
	
	@Autowired
	UserRepository userRepository;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String customToken = (String) authentication.getPrincipal();
		return getValidationToken(customToken);
	}

	private Authentication getValidationToken(String customToken) {
		// call auth service to check validity of token
		if(customToken.equalsIgnoreCase("UNSECURED_ENDPOINT")) {
			return new PreAuthenticatedAuthenticationToken("AuthenticatedUser", "ROLE_CLIENT");
		}
		
		boolean isValid = false;
		try {
			UserDTO user = SecurityUtil.validateAuthHeader(customToken);
			if(user == null) {
				isValid = false;
			} else if( userRepository.authUser(user.getEmail(), user.getPass()) != null ) {
				isValid = true;
			}
		} catch(Exception e) {
			isValid = false;
		}
		// keeping boolean flag for simplicity
		
		if (isValid)
			return new PreAuthenticatedAuthenticationToken("AuthenticatedUser", "ROLE_CLIENT");
		else
			throw new AccessDeniedException("Invalid authetication token");

	}

	@Override
	public boolean supports(Class<?> authentication) {
		// Lets use inbuilt token class for simplicity
		return PreAuthenticatedAuthenticationToken.class.equals(authentication);
	}
	
}
