package com.paypal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTaskDto {
	private Integer taskId;
	
	private String taskDesc;
	
	private String startDate;
	
	private String endDate;
	
	private String type;
	
	private String priority;
	
	private String status;
	
	private Integer newAssigneeId;
	
	private Integer newSprintId;
}
