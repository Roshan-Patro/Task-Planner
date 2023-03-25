package com.paypal.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paypal.dto.CreateSprintDto;
import com.paypal.exception.SprintException;
import com.paypal.exception.TaskException;
import com.paypal.exception.UserException;
import com.paypal.model.Sprint;
import com.paypal.model.Task;
import com.paypal.model.User;
import com.paypal.repository.SprintRepository;
import com.paypal.repository.TaskRepository;
import com.paypal.repository.UserRepository;

@Service
public class SprintServiceImpl implements SprintService {
	@Autowired
	private UserRepository urepo;

	@Autowired
	private TaskRepository trepo;

	@Autowired
	private SprintRepository srepo;

	@Override
	public Sprint createSprint(CreateSprintDto dto) throws SprintException, UserException {
		Optional<User> userOpt = urepo.findById(dto.getCreaterId());
		
		if(userOpt.isPresent()) {
			User existingUser = userOpt.get();
			
			Sprint newSprint = new Sprint();
			newSprint.setSprintDesc(dto.getSprintDesc());
			newSprint.setCreatedOn(LocalDate.parse(dto.getCreatedOn()));
			newSprint.setCreater(existingUser);
			
			existingUser.getCreatedSprints().add(newSprint);
			
			User updatedUser = urepo.save(existingUser);
			
			return updatedUser.getCreatedSprints().get(updatedUser.getCreatedSprints().size()-1);
		}
		throw new UserException("No user found with id: "+dto.getCreaterId()+". Please, try with a different creater id.");
	}

	@Override
	public Sprint addTaskToSprint(Integer sprintId, Integer taskId) throws SprintException, TaskException {
		Optional<Task> taskOpt = trepo.findById(taskId);
		if(taskOpt.isPresent()) {
			Task existingTask = taskOpt.get();
			
			Optional<Sprint> sprintOpt = srepo.findById(sprintId);
			
			if(sprintOpt.isPresent()) {
				Sprint existingSprint = sprintOpt.get();
				
				existingTask.setSprint(existingSprint);
				
				existingSprint.getTaskList().add(existingTask);
				
				return srepo.save(existingSprint);
			}
			throw new SprintException("Invalid sprint id: "+sprintId);
		}
		throw new TaskException("Invalid task id: "+taskId);
	}

}
