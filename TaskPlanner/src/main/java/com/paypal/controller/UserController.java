package com.paypal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paypal.dto.LoginUserDto;
import com.paypal.dto.RegisterUserDto;
import com.paypal.model.Task;
import com.paypal.model.User;
import com.paypal.repository.UserRepository;
import com.paypal.service.UserService;

@RestController
@RequestMapping("taskplanner/user")
public class UserController {
	
	@Autowired
	private UserService uservice;
	
	@Autowired
	private UserRepository urepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@PostMapping("/register")
	public ResponseEntity<User> registerAnUser(@RequestBody RegisterUserDto dto){
		dto.setPassword(passwordEncoder.encode(dto.getPassword()));
		User registeredUser = uservice.registerAnUser(dto);
		return new ResponseEntity<User>(registeredUser, HttpStatus.CREATED);
	}
	
//	Login controller with out spring security
	
//	@PostMapping("/login")
//	public ResponseEntity<User> loginUser(@RequestBody LoginUserDto dto){
//		User existingUser = uservice.loginUser(dto);
//		return new ResponseEntity<User>(existingUser, HttpStatus.ACCEPTED);
//	}
	
//	Login controller with spring security
	
	@GetMapping("/login")
	public ResponseEntity<User> getLoggedInUserDetails(Authentication auth){
		User user = urepository.findByEmail(auth.getName()).orElseThrow(() -> new BadCredentialsException("Invalid username or password."));
		
		return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
	}
	
	
	@GetMapping("/assignedtasks/{userId}")
	public ResponseEntity<List<Task>> getAssignedTasks(@PathVariable("userId") Integer userId){
		List<Task> assignedTasks = uservice.getAssignedTasks(userId);
		return new ResponseEntity<List<Task>>(assignedTasks, HttpStatus.OK);
	}
}
