package com.paypal.service;

import com.paypal.dto.CreateTaskDto;
import com.paypal.exception.TaskException;
import com.paypal.exception.UserException;
import com.paypal.model.Task;

public interface TaskService {
	public Task createTask(CreateTaskDto dto) throws TaskException, UserException;
}
