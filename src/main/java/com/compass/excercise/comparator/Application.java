package com.compass.excercise.comparator;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import service.ComparatorService;
import service.ProcessDataService;

@SpringBootApplication
public class Application {

	public static void main(String[] args)  {
		ProcessDataService processDataService = new ProcessDataService();
		ComparatorService comparatorService = new ComparatorService(processDataService);
		comparatorService.processContactInformation();
	}

}
