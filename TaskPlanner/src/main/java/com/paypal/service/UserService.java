package com.paypal.service;

import java.util.List;

import com.paypal.dto.LoginUserDto;
import com.paypal.dto.RegisterUserDto;
import com.paypal.dto.UpdateUserDto;
import com.paypal.exception.TaskException;
import com.paypal.exception.UserException;
import com.paypal.model.Task;
import com.paypal.model.User;

public interface UserService {
	public User registerAnUser(RegisterUserDto dto) throws UserException;
	
	public User loginUser(LoginUserDto dto) throws UserException;
	
	public List<Task> getAssignedTasks(Integer userId) throws UserException,TaskException;
	
	// Only an admin can delete a user
	public User deleteUserById(Integer userId) throws UserException;
	
	// Only an admin or the user itself can update the details
	public User updateUserDetails(UpdateUserDto dto) throws UserException;
	
	// Only admin can update an user's role
	public User updateUserRole(Integer userId, String newRole) throws UserException;
}
