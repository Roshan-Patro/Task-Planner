package com.paypal.service;

import java.util.List;

import com.paypal.dto.CreateTaskDto;
import com.paypal.dto.UpdateTaskDto;
import com.paypal.exception.SprintException;
import com.paypal.exception.TaskException;
import com.paypal.exception.UserException;
import com.paypal.model.Sprint;
import com.paypal.model.Task;
import com.paypal.model.User;

public interface TaskService {
	public Task createTask(CreateTaskDto dto) throws TaskException, UserException;

	public User assignTaskToUser(Integer taskId, Integer userId) throws TaskException, UserException;
	
	public User changeAssigneeOfTask(Integer taskId, Integer userId) throws TaskException, UserException;
	
	public Sprint changeSprintOfTask(Integer taskId, Integer sprintId) throws TaskException, SprintException;
	
	public Task changeStatusOfTask(Integer taskId, String newStatus) throws TaskException;
	
	public Task changePriorityOfTask(Integer taskId, String newPriority) throws TaskException;
	
	public Task changeStartDateOfTask(Integer taskId, String newStartDate) throws TaskException;
	
	public Task changeEndDateOfTask(Integer taskId, String newEndDate) throws TaskException;
	
	public Task changeTypeOfTask(Integer taskId, String newType) throws TaskException;
	
	public Task updateTask(UpdateTaskDto dto) throws TaskException;
	
	public List<Task> getAllTasks() throws TaskException;
	
	public List<Task> getTasksWithPagination(Integer pageNo, Integer pageSize) throws TaskException;
	
	// An Admin can delete any task. User can delete the tasks created by him/her only.
	public Task deleteTaskById(Integer taskId) throws TaskException, UserException;
}
