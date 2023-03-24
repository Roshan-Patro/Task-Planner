package com.paypal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		// TODO Auto-generated method stub
		return null;
	}
}
