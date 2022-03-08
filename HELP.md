### Steps to be followed to build and run docker image and application

* All below steps needs to be executed from project home directory

* Run below maven command to build war file 

	mvn clean package

* Run below 2 docker commands to build and run docker image from project root directory

	docker build --tag=knapsack:latest .
	docker run -8080:8080 knapsack:latest

* POST service URI : 
	
		http://localhost:8080/knapsack
* GET service URI : 
		
		http://localhost:8080/knapsack/{task}

### Sample json request

	{
	  "problem" : {
	    "capacity" : 60,
	    "weights" : [ 10, 20, 33 ],
	    "values" : [ 10, 3, 30 ]
	  }
	}	