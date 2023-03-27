package com.paypal.service;

import com.paypal.dto.CreateTaskDto;
import com.paypal.exception.SprintException;
import com.paypal.exception.TaskException;
import com.paypal.exception.UserException;
import com.paypal.model.Sprint;
import com.paypal.model.Task;
import com.paypal.model.User;

public interface TaskService {
	public Task createTask(CreateTaskDto dto) throws TaskException, UserException;

	public User assignTaskToUser(Integer taskId, Integer userId) throws TaskException, UserException;
	
	public User changeAssignee(Integer taskId, Integer userId) throws TaskException, UserException;
	
	public Sprint changeSprint(Integer taskId, Integer sprintId) throws TaskException, SprintException;
}
