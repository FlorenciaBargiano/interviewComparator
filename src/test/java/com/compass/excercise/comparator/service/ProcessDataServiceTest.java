package com.compass.excercise.comparator.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static com.compass.excercise.comparator.base.BaseServiceTest.getContactComparatorResult;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
class ProcessDataServiceTest {

	private ProcessDataService processDataService;

	@BeforeEach
	void setUp() {
		processDataService = new ProcessDataService();
	}

	@Test
	void obtainContactInformationShouldSuccess() {
		//THEN
		List<String[]> result = processDataService.obtainContactInformation();

		Assertions.assertThatCode(processDataService::obtainContactInformation)
				.doesNotThrowAnyException();

		assertEquals(result.size(), 1001);
		assertEquals(result.get(0).length, 6);
	}

	@Test
	void convertComparatorIntoACSVShouldSuccess() {
		List<String[]> contactComparatorResult = getContactComparatorResult();

		Assertions.assertThatCode(() -> processDataService.convertContactComparatorIntoACSVFile(contactComparatorResult))
				.doesNotThrowAnyException();
	}
}
