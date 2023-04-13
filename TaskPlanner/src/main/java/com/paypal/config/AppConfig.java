package com.paypal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class AppConfig {

	@Bean
	public SecurityFilterChain sprintSecurityConfiguration(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests()
		.requestMatchers(HttpMethod.POST, "taskplanner/user/register").permitAll()
//		.requestMatchers(HttpMethod.PUT, "/taskplanner/task/changesprint/**").hasRole("ADMIN")
		.requestMatchers(HttpMethod.GET, "/taskplanner/task/alltasks/**").hasRole("ADMIN")
		.anyRequest().authenticated().and()
		.csrf().disable()
		.addFilterAfter(new JwtTokenGeneratorFilter(), BasicAuthenticationFilter.class)
		.addFilterBefore(new JwtTokenValidatorFilter(), BasicAuthenticationFilter.class)
		.formLogin().and().httpBasic();
		
		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
