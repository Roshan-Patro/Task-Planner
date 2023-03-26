package com.paypal.service;

import java.util.List;

import com.paypal.dto.LoginUserDto;
import com.paypal.dto.RegisterUserDto;
import com.paypal.exception.TaskException;
import com.paypal.exception.UserException;
import com.paypal.model.Task;
import com.paypal.model.User;

public interface UserService {
	public User registerAnUser(RegisterUserDto dto) throws UserException;
	
	public User loginUser(LoginUserDto dto) throws UserException;
	
	public List<Task> getAssignedTasks(Integer userId) throws UserException,TaskException;
}
