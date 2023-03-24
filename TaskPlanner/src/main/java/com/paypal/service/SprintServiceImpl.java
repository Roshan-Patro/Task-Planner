package com.paypal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
