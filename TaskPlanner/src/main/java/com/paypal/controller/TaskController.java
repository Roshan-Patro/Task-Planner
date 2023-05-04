package com.paypal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paypal.dto.CreateTaskDto;
import com.paypal.dto.UpdateTaskDto;
import com.paypal.model.Sprint;
import com.paypal.model.Task;
import com.paypal.model.User;
import com.paypal.service.TaskService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/taskplanner/task")
public class TaskController {

	@Autowired
	private TaskService tservice;

	@PostMapping("/create")
	public ResponseEntity<Task> createTask(@Valid @RequestBody CreateTaskDto dto) {
		Task createdTask = tservice.createTask(dto);
		return new ResponseEntity<Task>(createdTask, HttpStatus.CREATED);
	}

	@PutMapping("/assigntouser/{taskId}/{userId}")
	public ResponseEntity<User> assignTaskToUser(@PathVariable("taskId") Integer taskId,
			@PathVariable("userId") Integer userId) {
		User assignedUser = tservice.assignTaskToUser(taskId, userId);
		return new ResponseEntity<User>(assignedUser, HttpStatus.OK);
	}

	@PutMapping("/changeassignee/{taskId}/{userId}")
	public ResponseEntity<User> changeAssigneeOfTask(@PathVariable("taskId") Integer taskId,
			@PathVariable("userId") Integer userId) {
		User assignedUser = tservice.changeAssigneeOfTask(taskId, userId);
		return new ResponseEntity<User>(assignedUser, HttpStatus.OK);
	}

	@PutMapping("/changesprint/{taskId}/{sprintId}")
	public ResponseEntity<Sprint> changeSprintOfTask(@PathVariable("taskId") Integer taskId,
			@PathVariable("sprintId") Integer sprintId) {
		Sprint addedSprint = tservice.changeSprintOfTask(taskId, sprintId);
		return new ResponseEntity<Sprint>(addedSprint, HttpStatus.OK);
	}

	@PutMapping("/changestatus/{taskId}/{newStatus}")
	public ResponseEntity<Task> changeStatusOfTask(@PathVariable("taskId") Integer taskId,
			@PathVariable("newStatus") String newStatus) {
		Task updatedTask = tservice.changeStatusOfTask(taskId, newStatus);
		return new ResponseEntity<Task>(updatedTask, HttpStatus.OK);
	}

	@PutMapping("/changepriority/{taskId}/{newPriority}")
	public ResponseEntity<Task> changePriorityOfTask(@PathVariable("taskId") Integer taskId,
			@PathVariable("newPriority") String newPriority) {
		Task updatedTask = tservice.changePriorityOfTask(taskId, newPriority);
		return new ResponseEntity<Task>(updatedTask, HttpStatus.OK);
	}

	@PutMapping("/changestartdate/{taskId}/{newStartDate}")
	public ResponseEntity<Task> changeStartDateOfTask(@PathVariable("taskId") Integer taskId,
			@PathVariable("newStartDate") String newStartDate) {
		Task updatedTask = tservice.changeStartDateOfTask(taskId, newStartDate);
		return new ResponseEntity<Task>(updatedTask, HttpStatus.OK);
	}

	@PutMapping("/changeenddate/{taskId}/{newEndDate}")
	public ResponseEntity<Task> changeEndDateOfTask(@PathVariable("taskId") Integer taskId,
			@PathVariable("newEndDate") String newEndDate) {
		Task updatedTask = tservice.changeEndDateOfTask(taskId, newEndDate);
		return new ResponseEntity<Task>(updatedTask, HttpStatus.OK);
	}

	@PutMapping("/changetype/{taskId}/{newType}")
	public ResponseEntity<Task> changeTypeOfTask(@PathVariable("taskId") Integer taskId,
			@PathVariable("newType") String newType) {
		Task updatedTask = tservice.changeTypeOfTask(taskId, newType);
		return new ResponseEntity<Task>(updatedTask, HttpStatus.OK);
	}

	@PutMapping("/update")
	public ResponseEntity<Task> updateTask(@RequestBody UpdateTaskDto dto) {
		Task updatedTask = tservice.updateTask(dto);
		return new ResponseEntity<Task>(updatedTask, HttpStatus.OK);
	}

	@GetMapping("/alltasks")
	public ResponseEntity<List<Task>> getAllTasks() {
		List<Task> allTasks = tservice.getAllTasks();
		return new ResponseEntity<List<Task>>(allTasks, HttpStatus.OK);
	}

	@GetMapping("/alltasks/{pageNo}/{pageSize}")
	public ResponseEntity<List<Task>> getTasksWithPagination(@PathVariable("pageNo") Integer pageNo,
			@PathVariable("pageSize") Integer pageSize) {
		List<Task> allTasks = tservice.getTasksWithPagination(pageNo, pageSize);
		return new ResponseEntity<List<Task>>(allTasks, HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{taskId}")
	public ResponseEntity<Task> deleteTaskById(@PathVariable("taskId") Integer taskId) {
		Task deletedTask = tservice.deleteTaskById(taskId);
		return new ResponseEntity<Task>(deletedTask, HttpStatus.OK);
	}
}
