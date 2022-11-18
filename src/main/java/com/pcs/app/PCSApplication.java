package com.pcs.app;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages= {"com.pcs.config","com.pcs.model","com.pcs.repo","com.pcs.service","com.pcs.controller","com.pcs.auth"})
@EntityScan(basePackages="com.pcs.model")
@SpringBootApplication( exclude = { SecurityAutoConfiguration.class } )
public class PCSApplication {
	public static void main(String[] args) {
		SpringApplication.run(PCSApplication.class, args);
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
