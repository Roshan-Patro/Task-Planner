//package com.paypal.service;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//import com.paypal.model.User;
//import com.paypal.repository.UserRepository;
//
//@Component
//public class MyAuthenticationProvider implements AuthenticationProvider {
//	
//	@Autowired
//	private UserRepository urepo;
//	
//	@Autowired
//	private PasswordEncoder pencoder;
//
//	@Override
//	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//		String username = authentication.getName();
//		String password = authentication.getCredentials().toString();
//		
//		Optional<User> userOpt = urepo.findByEmail(username);
//		
//		if(!userOpt.isPresent()) {
//			throw new BadCredentialsException("Invalid username.");
//		}
//		else {
//			User user = userOpt.get();
//			
//			if(pencoder.matches(password, user.getPassword())) {
//				List<GrantedAuthority> authorities = new ArrayList<>();
//				
//				return new UsernamePasswordAuthenticationToken(username, password, authorities);
//			}
//			else {
//				throw new BadCredentialsException("Invalid password.");
//			}
//		}
//	}
//
//	@Override
//	public boolean supports(Class<?> authentication) {
//		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
//	}
//
//}
