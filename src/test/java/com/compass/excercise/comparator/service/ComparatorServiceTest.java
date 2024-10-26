package com.compass.excercise.comparator.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static com.compass.excercise.comparator.base.BaseServiceTest.getContactInformation;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class ComparatorServiceTest {

	ProcessDataService processDataService;

	ComparatorService comparatorService;

	@BeforeEach
	public void setUp() {
		processDataService = mock(ProcessDataService.class);
		comparatorService = new ComparatorService(processDataService);
	}

	@Test
	void processContactInformationShouldSuccess() {

		//GIVEN
		List<String[]> contactInformation = getContactInformation();

		//WHEN
		when(processDataService.obtainContactInformation()).thenReturn(contactInformation);

		//THEN
		assertThatCode(comparatorService::processContactInformation)
				.doesNotThrowAnyException();

		verify(processDataService, times(1)).obtainContactInformation();
		verify(processDataService, times(1)).convertContactComparatorIntoACSVFile(any());
	}
}
