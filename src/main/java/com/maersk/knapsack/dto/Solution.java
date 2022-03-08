package com.maersk.knapsack.dto;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Solution {
	@JsonProperty(value = "packed_items")
	ArrayList<Integer> packedItems;
	@JsonProperty(value = "total_value")
	Integer totalValue;

}
