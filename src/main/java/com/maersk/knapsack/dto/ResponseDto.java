package com.maersk.knapsack.dto;

import lombok.Data;

@Data
public class ResponseDto {

	String task;
	String status;
	Timestamps timestamps;
	Problem problem;
	Solution solution;
}
