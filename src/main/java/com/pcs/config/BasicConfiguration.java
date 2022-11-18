package com.pcs.config;

import javax.servlet.Filter;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.pcs.auth.PCSTokenAuthenticationProvider;
import com.pcs.auth.PcsAuthenticationProcessingFilter;
import com.pcs.service.PcsUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableJpaRepositories(basePackages ={"com.pcs.repo", "com.pcs.model","com.pcs.auth"}) 
@EnableGlobalMethodSecurity(
		  prePostEnabled = true, 
		  securedEnabled = true, 
		  jsr250Enabled = true)
public class BasicConfiguration extends WebSecurityConfigurerAdapter{
	
	private static final RequestMatcher PROTECTED_URLS = new OrRequestMatcher(
			  new AntPathRequestMatcher("/android-client/**")
			 );

			PCSTokenAuthenticationProvider provider;

			 public BasicConfiguration(final PCSTokenAuthenticationProvider authenticationProvider) {
			  super();
			  this.provider = authenticationProvider;
			 }

			 @Override
			 public void configure(HttpSecurity http) throws Exception {
			  http.sessionManagement()
			   .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			   .and()
			   .exceptionHandling()
			   .and()
			   .addFilterBefore(authenticationFilter(), AnonymousAuthenticationFilter.class)
			   .authenticationProvider(provider)
			   .authorizeRequests()
			   .requestMatchers(PROTECTED_URLS)
			   .authenticated()
			   .and()
			   .csrf().disable()
			   .formLogin().disable()
			   .httpBasic().disable()
			   .logout().disable();
			 }
			 

			 PcsAuthenticationProcessingFilter authenticationFilter() throws Exception {
			  final PcsAuthenticationProcessingFilter filter = new PcsAuthenticationProcessingFilter(PROTECTED_URLS);
			  filter.setAuthenticationManager(authenticationManager());
			  //filter.setAuthenticationSuccessHandler(successHandler());
			  return filter;
			 }
}
