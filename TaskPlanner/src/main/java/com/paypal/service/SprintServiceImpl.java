package com.paypal.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			String currentUsername = authentication.getName();
			Integer currentUserId = urepo.findByEmail(currentUsername).get().getUserId();

			Sprint newSprint = new Sprint();
			newSprint.setSprintDesc(dto.getSprintDesc());
			newSprint.setCreatedOn(LocalDate.now());
			newSprint.setCreaterId(currentUserId);
			
			return srepo.save(newSprint);
		}
		throw new UserException("A sprint cannot be created by someone with anonymous authentication.");
	}

	@Override
	public Sprint addTaskToSprint(Integer sprintId, Integer taskId) throws SprintException, TaskException {
		Optional<Task> taskOpt = trepo.findById(taskId);
		if (taskOpt.isPresent()) {
			Task existingTask = taskOpt.get();

			Optional<Sprint> sprintOpt = srepo.findById(sprintId);

			if (sprintOpt.isPresent()) {
				Sprint existingSprint = sprintOpt.get();

				existingTask.setSprint(existingSprint);

				existingSprint.getTaskList().add(existingTask);

				return srepo.save(existingSprint);
			}
			throw new SprintException("Invalid sprint id: " + sprintId);
		}
		throw new TaskException("Invalid task id: " + taskId);
	}

	@Override
	public List<Task> getAddedTasks(Integer sprintId) throws SprintException, TaskException {
		Optional<Sprint> sprintOpt = srepo.findById(sprintId);

		if (sprintOpt.isPresent()) {
			Sprint existingSprint = sprintOpt.get();
			List<Task> addedTasks = existingSprint.getTaskList();

			if (!addedTasks.isEmpty()) {
				return addedTasks;
			}
			throw new TaskException("No added task yet in sprint: " + existingSprint.getSprintId());
		}
		throw new UserException("No sprint with id: " + sprintId + " found in the system...!");
	}

}
