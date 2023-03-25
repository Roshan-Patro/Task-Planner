package com.paypal.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paypal.dto.LoginUserDto;
import com.paypal.dto.RegisterUserDto;
import com.paypal.exception.UserException;
import com.paypal.model.User;
import com.paypal.repository.SprintRepository;
import com.paypal.repository.TaskRepository;
import com.paypal.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository urepo;
	
	@Autowired
	private TaskRepository trepo;
	
	@Autowired
	private SprintRepository srepo;

	@Override
	public User registerAnUser(RegisterUserDto dto) throws UserException {
		Optional<User> userOpt = urepo.findByEmail(dto.getEmail());
		
		if(userOpt.isEmpty()) {
			User newUser = new User();
			
			newUser.setUserName(dto.getUserName());
			newUser.setEmail(dto.getEmail());
			newUser.setAddress(dto.getAddress());
			newUser.setPassword(dto.getPassword());
			
			User savedUser = urepo.save(newUser);
			
			return savedUser;
		}
		throw new UserException("Email Id is already registered. Please use a different email id.");
	}

	@Override
	public User loginUser(LoginUserDto dto) throws UserException {
		Optional<User> userOpt = urepo.findByEmail(dto.getEmail());
		
		if(userOpt.isPresent()) {
			User existingUser = userOpt.get();
			
			if(existingUser.getPassword().equals(dto.getPassword())) {
				return existingUser;
			}
			
			throw new UserException("Incorrect password for the email id: "+dto.getEmail()+". Please try again with the correct one.");
		}
		throw new UserException("The email id: "+dto.getEmail()+" is not a registered email id.");
	}
}
