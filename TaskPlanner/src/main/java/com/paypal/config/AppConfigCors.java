package com.paypal.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class AppConfigCors implements WebMvcConfigurer {
	//For Enabling Cross Origin Requests for a RESTful Web Service  
		@Override
	    public void addCorsMappings(CorsRegistry corsRegistry) {
	        corsRegistry.addMapping("/**")
	                .allowedOriginPatterns("*")
	                .allowedMethods("*")
//	                .allowedMethods("GET", "POST", "PUT", "DELETE")
	                .maxAge(3600L)
	                .allowedHeaders("*")
	                .exposedHeaders("Authorization")
	                .allowCredentials(true);
	    }
}
