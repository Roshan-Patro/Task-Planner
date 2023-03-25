package com.paypal.service;

import java.time.LocalDate;
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
}
