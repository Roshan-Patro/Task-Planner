package com.paypal.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<CustomErrorDetails> anyExceptionHandler(Exception e, WebRequest w) {
		CustomErrorDetails err = new CustomErrorDetails(LocalDateTime.now(), e.getMessage(), w.getDescription(false));
		return new ResponseEntity<CustomErrorDetails>(err, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<CustomErrorDetails> noHandlerFoundExceptionHandler(NoHandlerFoundException e, WebRequest w) {
		CustomErrorDetails err = new CustomErrorDetails(LocalDateTime.now(), e.getMessage(), w.getDescription(false));
		return new ResponseEntity<CustomErrorDetails>(err, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<CustomErrorDetails> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
		CustomErrorDetails err = new CustomErrorDetails(LocalDateTime.now(), "Validation Error!", e.getBindingResult()
				.getFieldError()
				.getDefaultMessage());
		
		return new ResponseEntity<CustomErrorDetails>(err, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(UserException.class)
	public ResponseEntity<CustomErrorDetails> userExceptionHandler(UserException e, WebRequest w) {
		CustomErrorDetails err = new CustomErrorDetails(LocalDateTime.now(), e.getMessage(), w.getDescription(false));
		return new ResponseEntity<CustomErrorDetails>(err, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(TaskException.class)
	public ResponseEntity<CustomErrorDetails> taskExceptionHandler(TaskException e, WebRequest w) {
		CustomErrorDetails err = new CustomErrorDetails(LocalDateTime.now(), e.getMessage(), w.getDescription(false));
		return new ResponseEntity<CustomErrorDetails>(err, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(SprintException.class)
	public ResponseEntity<CustomErrorDetails> sprintExceptionHandler(SprintException e, WebRequest w) {
		CustomErrorDetails err = new CustomErrorDetails(LocalDateTime.now(), e.getMessage(), w.getDescription(false));
		return new ResponseEntity<CustomErrorDetails>(err, HttpStatus.BAD_REQUEST);
	}

}
