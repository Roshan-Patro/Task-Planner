package com.paypal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paypal.dto.RegisterUserDto;
import com.paypal.model.User;
import com.paypal.service.UserService;

@RestController
@RequestMapping("taskplanner/user")
public class UserController {
	
	@Autowired
	private UserService uservice;
	
	@PostMapping("/register")
	public ResponseEntity<User> registerAnUser(@RequestBody RegisterUserDto dto){
		User registeredUser = uservice.registerAnUser(dto);
		return new ResponseEntity<User>(registeredUser, HttpStatus.CREATED);
	}
}
