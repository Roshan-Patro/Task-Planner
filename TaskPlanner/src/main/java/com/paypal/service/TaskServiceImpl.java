package com.paypal.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paypal.dto.CreateTaskDto;
import com.paypal.enums.Priority;
import com.paypal.enums.Status;
import com.paypal.enums.Type;
import com.paypal.exception.TaskException;
import com.paypal.exception.UserException;
import com.paypal.model.Task;
import com.paypal.model.User;
import com.paypal.repository.SprintRepository;
import com.paypal.repository.TaskRepository;
import com.paypal.repository.UserRepository;

@Service
public class TaskServiceImpl implements TaskService {
	@Autowired
	private UserRepository urepo;

	@Autowired
	private TaskRepository trepo;

	@Autowired
	private SprintRepository srepo;

	@Override
	public Task createTask(CreateTaskDto dto) throws TaskException, UserException {
		Optional<User> userOpt = urepo.findById(dto.getCreaterId());

		if (userOpt.isPresent()) {
			Task newTask = new Task();
			newTask.setTaskDesc(dto.getTaskDesc());
			newTask.setCreaterId(dto.getCreaterId());
			newTask.setStartDate(LocalDate.parse(dto.getStartDate()));
			newTask.setEndDate(LocalDate.parse(dto.getEndDate()));
			newTask.setType(Type.valueOf(dto.getType()));
			newTask.setPriority(Priority.valueOf(dto.getPriority()));
			newTask.setStatus(Status.PENDING);

			Task savedTask = trepo.save(newTask);

			return savedTask;
		}
		throw new UserException("No user with id: " + dto.getCreaterId()
				+ " found in the system. Please, try with a different creater id.");

	}

	@Override
	public User assignTaskToUser(Integer taskId, Integer userId) throws TaskException, UserException {
		Optional<Task> taskOpt = trepo.findById(taskId);

		if (taskOpt.isPresent()) {
			Task existingTask = taskOpt.get();

			Optional<User> userOpt = urepo.findById(userId);
			if (userOpt.isPresent()) {
				User existingUser = userOpt.get();

				existingTask.setAssignee(existingUser);

				existingUser.getAssignedTasks().add(existingTask);

				User updatedUser = urepo.save(existingUser);

				return updatedUser;
			}
			throw new UserException("No user found with id: " + userId);
		}
		throw new TaskException("No task found with id: " + taskId);
	}

	@Override
	public User changeAssignee(Integer taskId, Integer userId) throws TaskException, UserException {
		Optional<Task> taskOpt = trepo.findById(taskId);

		if (taskOpt.isPresent()) {
			Task existingTask = taskOpt.get();

			Optional<User> userOpt = urepo.findById(userId);
			if (userOpt.isPresent()) {
				
				if(userId==existingTask.getAssignee().getUserId()) {
					throw new TaskException("Task is already assigned to the user: "+userId);
				}
				
				User existingUser = userOpt.get();

				User previousUser = existingTask.getAssignee();
				
				if (previousUser != null) {
					List<Task> currentTasks = previousUser.getAssignedTasks();
					currentTasks.removeIf(obj -> obj.getTaskId()==taskId);
					previousUser.setAssignedTasks(currentTasks);
					urepo.save(previousUser);
				}

				existingTask.setAssignee(existingUser);

				existingUser.getAssignedTasks().add(existingTask);

				User updatedUser = urepo.save(existingUser);
				
				return updatedUser;
			}
			throw new UserException("No user found with id: " + userId);
		}
		throw new TaskException("No task found with id: " + taskId);
	}
}
