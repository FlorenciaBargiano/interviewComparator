package com.compass.excercise.comparator;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import service.ComparatorService;

@SpringBootApplication
public class Application {

	public static void main(String[] args)  {
		ComparatorService.convertFileIntoList();
		//SpringApplication.run(Application.class, args);
	}

}
