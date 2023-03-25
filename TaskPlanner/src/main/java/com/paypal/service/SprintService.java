package com.paypal.service;

import com.paypal.dto.CreateSprintDto;
import com.paypal.exception.SprintException;
import com.paypal.exception.TaskException;
import com.paypal.exception.UserException;
import com.paypal.model.Sprint;

public interface SprintService {
	public Sprint createSprint(CreateSprintDto dto) throws SprintException, UserException;

	public Sprint addTaskToSprint(Integer sprintId, Integer taskId) throws SprintException, TaskException;
}
