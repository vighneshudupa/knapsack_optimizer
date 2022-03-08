package com.maersk.knapsack.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Service;

import com.maersk.knapsack.dto.RequestDto;
import com.maersk.knapsack.dto.ResponseDto;
import com.maersk.knapsack.dto.Solution;
import com.maersk.knapsack.dto.Timestamps;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KnapsackService {

	Map<String, ResponseDto> resultMap = new HashMap<>();

	public ResponseDto creaeKnapsackOptimiser(RequestDto requestDto) {
		log.info("Entered creaeKnapsackOptimiser");
		var responseDto = new ResponseDto();
		responseDto.setStatus("submitted");
		String taskId = randomAscii();
		responseDto.setTask(taskId);
		var timestamps = new Timestamps();
		timestamps.setStarted(Instant.now().toEpochMilli());
		responseDto.setProblem(requestDto.getProblem());
		responseDto.setTimestamps(timestamps);
		//Submit async process for optimizer
		log.info("Submitted async task to optimiser");
		CompletableFuture
				.supplyAsync(() -> knapSack(taskId, responseDto, requestDto.getProblem().getValues(),
						requestDto.getProblem().getWeights(), requestDto.getProblem().getCapacity()));
		resultMap.put(taskId, responseDto);
		return responseDto;
	}

	/**
	 * Find the optimizer for given input
	 * @param taskId
	 * @param responseDto
	 * @param values
	 * @param weights
	 * @param capacity
	 * @return ResponseDto
	 */
	ResponseDto knapSack(String taskId, ResponseDto responseDto, int[] values, int[] weights, int capacity) {
		log.info("Executing async task of optimiser");
		var timestamps = responseDto.getTimestamps();
		timestamps.setSubmitted(Instant.now().toEpochMilli());
		int i, cap;
		int length = values.length;
		int[][] weightAndValueMatrix = new int[length + 1][capacity + 1];

		for (i = 0; i <= length; i++) {
			for (cap = 0; cap <= capacity; cap++) {
				if (i == 0 || cap == 0)
					weightAndValueMatrix[i][cap] = 0;
				else if (weights[i - 1] <= cap)
					weightAndValueMatrix[i][cap] = max(values[i - 1] + weightAndValueMatrix[i - 1][cap - weights[i - 1]],
							weightAndValueMatrix[i - 1][cap]);
				else
					weightAndValueMatrix[i][cap] = weightAndValueMatrix[i - 1][cap];
			}
		}
		var solution = new Solution();
		solution.setTotalValue(weightAndValueMatrix[length][capacity]);
		ArrayList<Integer> indexes = new ArrayList<>();

		while (length != 0) {
			if (weightAndValueMatrix[length][capacity] != weightAndValueMatrix[length - 1][capacity]) {
				indexes.add(length - 1);
				capacity = capacity - weights[length - 1];
			}
			length--;
		}
		Collections.reverse(indexes);
		solution.setPackedItems(indexes);

		timestamps.setCompleted(Instant.now().toEpochMilli());
		responseDto.setTimestamps(timestamps);
		responseDto.setSolution(solution);
		responseDto.setStatus("completed");
		resultMap.put(taskId, responseDto);
		log.info("Completed executing async task of optimiser");
		return responseDto;
	}

	int max(int var1, int var2) {
		return (var1 > var2) ? var1 : var2;
	}

	String randomAscii() {
		return new Random().ints(48, 122 + 1).filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)).limit(10)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
	}
	
	/**
	 * Fetch the optimiser result for given task ID
	 * @param id
	 * @return
	 */
	public ResponseDto fetchKnapsackOptimiser(String id) {

		return resultMap.get(id);
	}

}
