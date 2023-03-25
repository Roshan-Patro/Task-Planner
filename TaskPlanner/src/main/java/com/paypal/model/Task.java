package com.paypal.model;

import java.time.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.paypal.enums.Priority;
import com.paypal.enums.Status;
import com.paypal.enums.Type;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer taskId;

	private String taskDesc;

	private Integer createrId;

	private LocalDate startDate;

	private LocalDate endDate;

	@Enumerated(EnumType.STRING)
	private Type type;

	@Enumerated(EnumType.STRING)
	private Priority priority;

	@Enumerated(EnumType.STRING)
	private Status status;

	@JsonIgnore
	@ManyToOne // Bidirectional
	private User assignee;

	@JsonIgnore
	@ManyToOne // Bidirectional
	private Sprint sprint;

}
