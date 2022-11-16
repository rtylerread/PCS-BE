package com.pcs.auth;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import com.pcs.model.UserDTO;
import com.pcs.util.SecurityUtil;

public class PcsAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {
	
	private static final String NO_AUTH_ENDPOINTS = "/test,/android-client/auth";
	
	public PcsAuthenticationProcessingFilter(RequestMatcher requiresAuthenticationRequestMatcher,
			AuthenticationManager authenticationManager) {
		super(requiresAuthenticationRequestMatcher);
		//Set authentication manager
		setAuthenticationManager(authenticationManager);
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		// Extract from request
		String header = request.getHeader("X-Auth");
		PreAuthenticatedAuthenticationToken token;
		// Create a token object ot pass to Authentication Provider
		if(Arrays.asList(NO_AUTH_ENDPOINTS.split(",")).contains(request.getContextPath())) {
			 token = new PreAuthenticatedAuthenticationToken("UNSECURED_ENDPOINT", null);
		} else {
			 token = new PreAuthenticatedAuthenticationToken(header, null);
		}
		
		return getAuthenticationManager().authenticate(token);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		// Save user principle in security context
		SecurityContextHolder.getContext().setAuthentication(authResult);
		chain.doFilter(request, response);
	}

}
