package com.maersk.knapsack.dto;

import lombok.Data;

@Data
public class Problem {
	Integer capacity;
	int[] weights;
	int[] values;
}
