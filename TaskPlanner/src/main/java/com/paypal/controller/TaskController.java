package com.paypal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paypal.dto.CreateTaskDto;
import com.paypal.model.Sprint;
import com.paypal.model.Task;
import com.paypal.model.User;
import com.paypal.service.TaskService;

@RestController
@RequestMapping("/taskplanner/task")
public class TaskController {
	
	@Autowired
	private TaskService tservice;
	
	@PostMapping("/create")
	public ResponseEntity<Task> createTask(@RequestBody CreateTaskDto dto){
		Task createdTask = tservice.createTask(dto);
		return new ResponseEntity<Task>(createdTask, HttpStatus.CREATED);
	}
	
	@PutMapping("/assigntouser/{taskId}/{userId}")
	public ResponseEntity<User> assignTaskToUser(@PathVariable("taskId") Integer taskId, @PathVariable("userId") Integer userId){
		User assignedUser = tservice.assignTaskToUser(taskId,userId);
		return new ResponseEntity<User>(assignedUser, HttpStatus.OK);
	}
	
	@PutMapping("/changeassignee/{taskId}/{userId}")
	public ResponseEntity<User> changeAssignee(@PathVariable("taskId") Integer taskId, @PathVariable("userId") Integer userId){
		User assignedUser = tservice.changeAssignee(taskId,userId);
		return new ResponseEntity<User>(assignedUser, HttpStatus.OK);
	}
	
	@PutMapping("/changesprint/{taskId}/{sprintId}")
	public ResponseEntity<Sprint> changeSprint(@PathVariable("taskId") Integer taskId, @PathVariable("sprintId") Integer sprintId){
		Sprint addedSprint = tservice.changeSprint(taskId,sprintId);
		return new ResponseEntity<Sprint>(addedSprint, HttpStatus.OK);
	}
}
