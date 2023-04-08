package com.paypal.dto;

import jakarta.validation.constraints.FutureOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTaskDto {
	private String taskDesc;
//	private Integer createrId;
	
	@FutureOrPresent(message = "Start date cannot be a past date.")
	private String startDate;
	private String endDate;
	private String type;
	private String priority;
}
