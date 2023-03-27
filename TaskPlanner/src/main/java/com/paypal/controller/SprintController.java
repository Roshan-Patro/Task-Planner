package com.paypal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paypal.dto.CreateSprintDto;
import com.paypal.model.Sprint;
import com.paypal.model.Task;
import com.paypal.service.SprintService;

@RestController
@RequestMapping("/taskplanner/sprint")
public class SprintController {
	@Autowired
	private SprintService sservice;

	@PostMapping("/create")
	public ResponseEntity<Sprint> createSprint(@RequestBody CreateSprintDto dto) {
		Sprint createdSprint = sservice.createSprint(dto);
		return new ResponseEntity<Sprint>(createdSprint, HttpStatus.CREATED);
	}

	@PutMapping("/addtask/{sprintId}/{taskId}")
	public ResponseEntity<Sprint> addTaskToSprint(@PathVariable("sprintId") Integer sprintId,
			@PathVariable("taskId") Integer taskId) {
		Sprint updatedSprint = sservice.addTaskToSprint(sprintId, taskId);
		return new ResponseEntity<Sprint>(updatedSprint, HttpStatus.OK);
	}
	
	@GetMapping("/addedtasks/{sprintId}")
	public ResponseEntity<List<Task>> getAddedTasks(@PathVariable("sprintId") Integer sprintId){
		List<Task> addedTasks = sservice.getAddedTasks(sprintId);
		return new ResponseEntity<List<Task>>(addedTasks, HttpStatus.OK);
	}
}
