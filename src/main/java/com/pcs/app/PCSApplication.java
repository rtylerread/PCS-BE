package com.pcs.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages= {"com.pcs.config","com.pcs.model","com.pcs.repo","com.pcs.service","com.pcs.controller","com.pcs.auth"})
@EntityScan(basePackages="com.pcs.model")
@SpringBootApplication( exclude = { SecurityAutoConfiguration.class } )
public class PCSApplication {
	public static void main(String[] args) {
		SpringApplication.run(PCSApplication.class, args);
	}
}
