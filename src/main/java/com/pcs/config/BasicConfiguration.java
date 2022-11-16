package com.pcs.config;

import javax.servlet.Filter;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.pcs.auth.PCSTokenAuthenticationProvider;
import com.pcs.auth.PcsAuthenticationProcessingFilter;

@Configuration
@EnableWebSecurity
@EnableJpaRepositories(basePackages ={"com.pcs.repo", "com.pcs.model"}) 
@EnableGlobalMethodSecurity(
		  prePostEnabled = true, 
		  securedEnabled = true, 
		  jsr250Enabled = true)
public class BasicConfiguration extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private PCSTokenAuthenticationProvider pcsTokenAuthenticationProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests((authorize) -> authorize
//                .mvcMatchers("*android-client/auth*").permitAll()
//                .anyRequest().permitAll())
//        .addFilterBefore(new AuthFilter(), BasicAuthenticationFilter.class);
//        return http.build();
    	
    	http.httpBasic().disable() // No Http Basic Login
		.csrf().disable() // No CSRF token
		.formLogin().disable() // No Form Login
		.logout().disable() // No Logout
		// No Session pls
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
		.authenticationProvider(pcsTokenAuthenticationProvider)
		.addFilterBefore(getFilter(), AnonymousAuthenticationFilter.class).authorizeRequests() // Authorize
																								// requests
		.requestMatchers(getRequestMatchers()).authenticated();
    	
    	return http.build();
}

	private RequestMatcher getRequestMatchers() {
		return new OrRequestMatcher(new AntPathRequestMatcher("/**"));
	}
	
	private Filter getFilter() throws Exception {
		return new PcsAuthenticationProcessingFilter(getRequestMatchers(), authenticationManager());
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(pcsTokenAuthenticationProvider);
	}
    
    @Bean
	public DataSource getDataSource()
	{
    	DataSourceBuilder dsb = DataSourceBuilder.create();
    	dsb.driverClassName("com.mysql.jdbc.Driver");
    	dsb.url("jdbc:mysql://database-1-instance-1.cwdhottft4rp.us-east-1.rds.amazonaws.com:3306/auroradb");
    	dsb.username("admin");
    	dsb.password("s8?n&fJRH!PSLKQA");
    	return dsb.build();
	}
}
