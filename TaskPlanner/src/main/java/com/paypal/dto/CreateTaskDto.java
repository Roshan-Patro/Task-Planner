package com.paypal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTaskDto {
	private String taskDesc;
//	private Integer createrId;

	private String startDate;
	private String endDate;
	private String type;
	private String priority;
}
