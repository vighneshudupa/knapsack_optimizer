package com.maersk.knapsack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maersk.knapsack.dto.RequestDto;
import com.maersk.knapsack.dto.ResponseDto;
import com.maersk.knapsack.service.KnapsackService;

import lombok.extern.slf4j.Slf4j;

/**
 * Main controller class to accept knapsack problem input json object using POST method and get the result using a GET method
 * @author vighnesh udupa
 *
 */
@Slf4j
@RestController
@RequestMapping("knapsack")
public class KnapsackController {
	
	@Autowired
	KnapsackService knapsackService;
	
	@PostMapping
	public ResponseDto creaeKnapsackOptimiser(@RequestBody RequestDto requestDto)
	{
		log.info("Running Knapsack Optimiser for the given input");
		return knapsackService.creaeKnapsackOptimiser(requestDto);
	}
	
	@GetMapping("{task}")
	public ResponseDto fetchKnapsackOptimiser(@PathVariable(value = "task") String task )
	{
		log.info("Fetching Knapsack Optimiser result for the given task ID");
		return knapsackService.fetchKnapsackOptimiser(task);
	}

}
