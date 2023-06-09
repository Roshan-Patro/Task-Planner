package com.paypal.service;

import java.util.List;

import com.paypal.dto.CreateSprintDto;
import com.paypal.dto.UpdateSprintDescDto;
import com.paypal.exception.SprintException;
import com.paypal.exception.TaskException;
import com.paypal.exception.UserException;
import com.paypal.model.Sprint;
import com.paypal.model.Task;

public interface SprintService {
	public Sprint createSprint(CreateSprintDto dto) throws SprintException, UserException;

	public Sprint addTaskToSprint(Integer sprintId, Integer taskId) throws SprintException, TaskException;

	public List<Task> getAddedTasks(Integer sprintId) throws SprintException, TaskException;

	// Only admin or creator of a sprint can delete the sprint
	public Sprint deleteSprintById(Integer sprintId) throws SprintException;

	// Only admin or creator of a sprint can update the sprint
	public Sprint updateSprintDesc(Integer sprintId, UpdateSprintDescDto dto) throws SprintException;
}
