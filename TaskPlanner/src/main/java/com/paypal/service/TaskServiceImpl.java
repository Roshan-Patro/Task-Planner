package com.paypal.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.paypal.dto.CreateTaskDto;
import com.paypal.dto.UpdateTaskDto;
import com.paypal.enums.Priority;
import com.paypal.enums.Status;
import com.paypal.enums.Type;
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
public class TaskServiceImpl implements TaskService {
	@Autowired
	private UserRepository urepo;

	@Autowired
	private TaskRepository trepo;

	@Autowired
	private SprintRepository srepo;

	@Override
	public Task createTask(CreateTaskDto dto) throws TaskException, UserException {
//		Optional<User> userOpt = urepo.findById(dto.getCreaterId());

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentUserName = authentication.getName();
		User existingUser = urepo.findByEmail(currentUserName).get();
		
//		if (userOpt.isPresent()) {
			Task newTask = new Task();
			newTask.setTaskDesc(dto.getTaskDesc());
			newTask.setCreatorId(existingUser.getUserId());
			newTask.setStartDate(LocalDate.parse(dto.getStartDate()));
			newTask.setEndDate(LocalDate.parse(dto.getEndDate()));
			newTask.setType(Type.valueOf(dto.getType().toUpperCase()));
			newTask.setPriority(Priority.valueOf(dto.getPriority().toUpperCase()));
			newTask.setStatus(Status.PENDING);

			Task savedTask = trepo.save(newTask);

			return savedTask;
//		}
//		throw new UserException("No user with id: " + dto.getCreaterId()
//				+ " found in the system. Please, try with a different creater id.");

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
	public User changeAssigneeOfTask(Integer taskId, Integer userId) throws TaskException, UserException {
		Optional<Task> taskOpt = trepo.findById(taskId);

		if (taskOpt.isPresent()) {
			Task existingTask = taskOpt.get();

			Optional<User> userOpt = urepo.findById(userId);
			if (userOpt.isPresent()) {

				if (userId == existingTask.getAssignee().getUserId()) {
					throw new TaskException("Task is already assigned to the user: " + userId);
				}

				User existingUser = userOpt.get();

				User previousUser = existingTask.getAssignee();

				if (previousUser != null) {
					List<Task> currentTasks = previousUser.getAssignedTasks();
					currentTasks.removeIf(obj -> obj.getTaskId() == taskId);
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

	@Override
	public Sprint changeSprintOfTask(Integer taskId, Integer sprintId) throws TaskException, SprintException {
		Optional<Task> taskOpt = trepo.findById(taskId);

		if (taskOpt.isPresent()) {
			Task existingTask = taskOpt.get();

			Optional<Sprint> sprintOpt = srepo.findById(sprintId);
			if (sprintOpt.isPresent()) {

				if (existingTask.getSprint()!=null && sprintId == existingTask.getSprint().getSprintId()) {
					throw new TaskException("Task is already added to the sprint: " + sprintId);
				}

				Sprint existingSprint = sprintOpt.get();

				Sprint previousSprint = existingTask.getSprint();

				if (previousSprint != null) {
					List<Task> currentTasks = previousSprint.getTaskList();
					currentTasks.removeIf(obj -> obj.getTaskId() == taskId);
					previousSprint.setTaskList(currentTasks);
					srepo.save(previousSprint);
				}

				existingTask.setSprint(existingSprint);

				existingSprint.getTaskList().add(existingTask);

				Sprint updatedSprint = srepo.save(existingSprint);

				return updatedSprint;
			}
			throw new SprintException("No sprint found with id: " + sprintId);
		}
		throw new TaskException("No task found with id: " + taskId);
	}

	@Override
	public Task changeStatusOfTask(Integer taskId, String newStatus) throws TaskException {
		Optional<Task> taskOpt = trepo.findById(taskId);

		if (taskOpt.isPresent()) {
			Task existingTask = taskOpt.get();

			if (!newStatus.toUpperCase().equals("PENDING") && !newStatus.toUpperCase().equals("COMPLETED")
					&& !newStatus.toUpperCase().equals("CANCELED")) {
				throw new TaskException("Please enter a valid status. (PENDING / COMPLETED / CANCELED)");
			}

			if (newStatus.toUpperCase().equals(existingTask.getStatus().toString())) {
				throw new TaskException("The status of the task is already: " + newStatus);
			}

			existingTask.setStatus(Status.valueOf(newStatus.toUpperCase()));

			Task updatedTask = trepo.save(existingTask);

			return updatedTask;
		}
		throw new TaskException("No task found with id: " + taskId);
	}

	@Override
	public Task changePriorityOfTask(Integer taskId, String newPriority) throws TaskException {
		Optional<Task> taskOpt = trepo.findById(taskId);

		if (taskOpt.isPresent()) {
			Task existingTask = taskOpt.get();

			if (!newPriority.toUpperCase().equals("VERYLOW") && !newPriority.toUpperCase().equals("LOW")
					&& !newPriority.toUpperCase().equals("MEDIUM") && !newPriority.toUpperCase().equals("HIGH")
					&& !newPriority.toUpperCase().equals("CRITICAL")) {
				throw new TaskException("Please enter a valid status. (VERYLOW / LOW / MEDIUM / HIGH / CRITICAL)");
			}

			if (newPriority.toUpperCase().equals(existingTask.getPriority().toString())) {
				throw new TaskException("The status of the task is already: " + newPriority);
			}

			existingTask.setPriority(Priority.valueOf(newPriority.toUpperCase()));

			Task updatedTask = trepo.save(existingTask);

			return updatedTask;
		}
		throw new TaskException("No task found with id: " + taskId);
	}

	@Override
	public Task changeStartDateOfTask(Integer taskId, String newStartDate) throws TaskException {
		Optional<Task> taskOpt = trepo.findById(taskId);

		if (taskOpt.isPresent()) {
			Task existingTask = taskOpt.get();

			if (existingTask.getStartDate().isEqual(LocalDate.parse(newStartDate))) {
				throw new TaskException("The start date is already: " + LocalDate.parse(newStartDate));
			}

			existingTask.setStartDate(LocalDate.parse(newStartDate));
			Task updatedTask = trepo.save(existingTask);

			return updatedTask;
		} else {
			throw new TaskException("No task found with id: " + taskId);
		}
	}

	@Override
	public Task changeEndDateOfTask(Integer taskId, String newEndDate) throws TaskException {
		Optional<Task> taskOpt = trepo.findById(taskId);

		if (taskOpt.isPresent()) {
			Task existingTask = taskOpt.get();

			if (existingTask.getEndDate().isEqual(LocalDate.parse(newEndDate))) {
				throw new TaskException("The end date is already: " + LocalDate.parse(newEndDate));
			}

			existingTask.setEndDate(LocalDate.parse(newEndDate));
			Task updatedTask = trepo.save(existingTask);

			return updatedTask;
		} else {
			throw new TaskException("No task found with id: " + taskId);
		}
	}

	@Override
	public Task changeTypeOfTask(Integer taskId, String newType) throws TaskException {
		Optional<Task> taskOpt = trepo.findById(taskId);

		if (taskOpt.isPresent()) {
			Task existingTask = taskOpt.get();

			if (!newType.toUpperCase().equals("BUG") && !newType.toUpperCase().equals("FEATURE")
					&& !newType.toUpperCase().equals("STORY")) {
				throw new TaskException("Please enter a valid type. (BUG / FEATURE / STORY)");
			}

			if (newType.toUpperCase().equals(existingTask.getType().toString())) {
				throw new TaskException("The type of the task is already: " + newType);
			}

			existingTask.setType(Type.valueOf(newType.toUpperCase()));
			Task updatedTask = trepo.save(existingTask);

			return updatedTask;
		} else {
			throw new TaskException("No task found with id: " + taskId);
		}
	}

	@Override
	public Task updateTask(UpdateTaskDto dto) throws TaskException {
		Optional<Task> taskOpt = trepo.findById(dto.getTaskId());

		if (taskOpt.isPresent()) {
			Task existingTask = taskOpt.get();

			existingTask.setTaskDesc(dto.getTaskDesc());
			existingTask.setStartDate(LocalDate.parse(dto.getStartDate()));
			existingTask.setEndDate(LocalDate.parse(dto.getEndDate()));
			existingTask.setType(Type.valueOf(dto.getType().toUpperCase()));
			existingTask.setPriority(Priority.valueOf(dto.getPriority().toUpperCase()));
			existingTask.setStatus(Status.valueOf(dto.getStatus().toUpperCase()));

			// Updating assignee
			Optional<User> userOpt = urepo.findById(dto.getNewAssigneeId());
			if (userOpt.isPresent()) {
				if (existingTask.getAssignee() != null
						&& (existingTask.getAssignee().getUserId() != dto.getNewAssigneeId())) {
					User previousAssignee = existingTask.getAssignee();

					previousAssignee.getAssignedTasks().removeIf(obj -> obj.getTaskId() == existingTask.getTaskId());

					existingTask.setAssignee(userOpt.get());

					userOpt.get().getAssignedTasks().add(existingTask);
				} else if (existingTask.getAssignee() == null) {

					existingTask.setAssignee(userOpt.get());

					userOpt.get().getAssignedTasks().add(existingTask);
				}
			} else {
				throw new TaskException("No user found with id: " + dto.getNewAssigneeId() + " to assign.");
			}

			// Updating sprint
			Optional<Sprint> sprintOpt = srepo.findById(dto.getNewSprintId());
			if (sprintOpt.isPresent()) {
				if (existingTask.getSprint() != null
						&& (existingTask.getSprint().getSprintId() != dto.getNewSprintId())) {
					Sprint previousSprint = existingTask.getSprint();

					previousSprint.getTaskList().removeIf(obj -> obj.getTaskId() == existingTask.getTaskId());

					existingTask.setSprint(sprintOpt.get());

					sprintOpt.get().getTaskList().add(existingTask);
				} else if (existingTask.getSprint() == null) {

					existingTask.setSprint(sprintOpt.get());

					sprintOpt.get().getTaskList().add(existingTask);
				}
			} else {
				throw new TaskException("No sprint found with id: " + dto.getNewSprintId() + " to add.");
			}

			Task updatedTask = trepo.save(existingTask);

			return updatedTask;

		}
		throw new TaskException("No task found with id: " + dto.getTaskId());
	}

	@Override
	public List<Task> getAllTasks() throws TaskException {
		List<Task> allTasks = trepo.findAll();

		if (!allTasks.isEmpty()) {
			return allTasks;
		}
		throw new TaskException("No task found in system.");
	}

	@Override
	public List<Task> getTasksWithPagination(Integer pageNo, Integer pageSize) throws TaskException {
		Pageable pObj = PageRequest.of(pageNo, pageSize);
		if (pObj.isPaged()) {
			Page<Task> tasksPage = trepo.findAll(pObj);
			List<Task> tasksList = tasksPage.getContent();
			if (!tasksList.isEmpty()) {
				return tasksList;
			}
			throw new TaskException("No data found for page number: " + pageNo + " and page size: " + pageSize);

		} else {
			throw new TaskException("No pagination information found...!");
		}

	}

	@Override
	public Task deleteTaskById(Integer taskId) throws TaskException, UserException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			String currentUserName = authentication.getName();
			Optional<User> userOpt = urepo.findByEmail(currentUserName);
			User existingUser = userOpt.get();

			if (existingUser.getRole().equals("ROLE_ADMIN")) {
				Optional<Task> taskOpt = trepo.findById(taskId);
				if (taskOpt.isPresent()) {
					Task existingTask = taskOpt.get();
					trepo.delete(existingTask);
					return existingTask;
				}
				throw new TaskException("Invalid task id: " + taskId);
			} else if (existingUser.getRole().equals("ROLE_USER")) {
				Optional<Task> taskOpt = trepo.findById(taskId);
				if (taskOpt.isPresent()) {
					Task existingTask = taskOpt.get();
					User createrOfTask = urepo.findById(existingTask.getCreatorId()).get();
					if(createrOfTask.getEmail().equals(existingUser.getEmail())) {
						trepo.delete(existingTask);
						return existingTask;
					}
					throw new TaskException("Apart from admin, only the creator of a task can delete the task.");
				}
				throw new TaskException("Invalid task id: " + taskId);
			}
			else {
				throw new TaskException("Apart from Admin and User no one else can delete a task.");
			}
		}
		throw new UserException(
				"Sorry! This task cannot be deleted. Tasks cannot be deleted with anonymous authentication.");

	}
}
