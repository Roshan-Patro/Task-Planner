package com.paypal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	
	@PostMapping("/login")
	public ResponseEntity<User> loginUser(@RequestBody LoginUserDto dto){
		User existingUser = uservice.loginUser(dto);
		return new ResponseEntity<User>(existingUser, HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/assignedtasks/{userId}")
	public ResponseEntity<List<Task>> getAssignedTasks(@PathVariable("userId") Integer userId){
		List<Task> assignedTasks = uservice.getAssignedTasks(userId);
		return new ResponseEntity<List<Task>>(assignedTasks, HttpStatus.OK);
	}
}
