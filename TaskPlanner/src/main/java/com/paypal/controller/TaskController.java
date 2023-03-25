package com.paypal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paypal.dto.CreateTaskDto;
import com.paypal.model.Task;
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
}
