package com.paypal.service;

import com.paypal.dto.RegisterUserDto;
import com.paypal.exception.UserException;
import com.paypal.model.User;

public interface UserService {
	public User registerAnUser(RegisterUserDto dto) throws UserException;
}
