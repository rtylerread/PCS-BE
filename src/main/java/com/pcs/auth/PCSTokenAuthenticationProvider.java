package com.pcs.auth;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

import com.pcs.model.UserDTO;
import com.pcs.repo.UserRepository;
import com.pcs.util.SecurityUtil;

@Component
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
		if("UNSECURED_ENDPOINT".equalsIgnoreCase(customToken)) {
			return new PreAuthenticatedAuthenticationToken("AuthenticatedUser", "ROLE_CLIENT");
		}
		
		boolean isValid = false;
		try {
			System.out.println("attempting to validate with header ["+customToken+"]");
			UserDTO user = SecurityUtil.validateAuthHeader(customToken);
			if(user == null) {
				isValid = false;
			} else if( userRepository.authUser(user.getEmail(), user.getPass()) != null ) {
				isValid = true;
			}
		} catch(Exception e) {
			e.printStackTrace();
			isValid = false;
		}
		// keeping boolean flag for simplicity
		
		if (isValid)
			return new PreAuthenticatedAuthenticationToken(customToken, customToken);
		else
			throw new AccessDeniedException("Invalid authetication token: "+customToken);

	}

	@Override
	public boolean supports(Class<?> authentication) {
		// Lets use inbuilt token class for simplicity
		return PreAuthenticatedAuthenticationToken.class.equals(authentication);
	}
	
}
