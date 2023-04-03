package com.paypal.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.paypal.model.User;
import com.paypal.repository.UserRepository;

@Service
public class CustomerUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> userOpt = userRepository.findByEmail(username);
		
		if(userOpt.isPresent()) {
			User existingUser = userOpt.get();
			
			List<GrantedAuthority> authorities = new ArrayList<>();
			
			SimpleGrantedAuthority simpleGrantedAuthority= new SimpleGrantedAuthority(existingUser.getRole());
			authorities.add(simpleGrantedAuthority);
			
			return new org.springframework.security.core.userdetails.User(existingUser.getEmail(), existingUser.getPassword(), authorities);
		}
		throw new BadCredentialsException("No user found with username: "+username);
	}
	
	

}
