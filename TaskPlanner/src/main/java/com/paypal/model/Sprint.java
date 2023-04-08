package com.paypal.model;

import java.time.*;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Sprint {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer sprintId;

	private String sprintDesc;

	private LocalDate createdOn;

//	@JsonIgnore
//	@ManyToOne // Bidirectional
//	private User creater;
	
	private Integer creatorId;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "sprint") // Bidirectional
	private List<Task> taskList;

}
