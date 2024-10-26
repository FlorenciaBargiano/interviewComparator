package com.compass.excercise.comparator.service;

import com.github.wslf.levenshteindistance.LevenshteinCalculator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ComparatorService {
    private static final String[] RESULT_HEADERS = new String[] { "ContactID Source", "ContactID Match", "Result", "Accuracy" };


    /*
        The idea is to make the logic of getting the information from and
        converting the information into a CSV file completely independent
        of the process of the data. Doing so, we allow in the future to
        add new sources, and it will not affect this service
    */
    private final ProcessDataService processDataService;

    /*
          ----- Comparator Logic ----
          We can divide the process required into these steps:
          Step 1 > Obtain Contact Data from CSV
          Step 2 > Compare Contact Information
          Step 3 > Convert the result into a CSV file
    */
    public void processContactInformation() {
        //Step 1
        System.out.println("Initiating to compare contact information");
        List<String[]> contactInformation = processDataService.obtainContactInformation();

        //Step 2
        List<String[]> contactInformationCompared = compareContactInformation(contactInformation);

        //Step 3
        processDataService.convertContactComparatorIntoACSVFile(contactInformationCompared);
        System.out.println("The process ended successfully, the file was created/updated (if already existed)");
    }

    private List<String[]> compareContactInformation(List<String[]> contactInformation) {
        double result = 0;

        //This lines below defines the structure of the CSV we obtain as a result
        List<String[]> comparatorResult = new ArrayList<>();
        comparatorResult.add(RESULT_HEADERS);

        //Contact-Source Elements: We avoid calling to i = 0 because it represents the Headers of the CSV
        for (int i = 1; i < contactInformation.size(); i++) {

            /* Contact-Match Elements. These are the comparator elements so we begin
                from the (source-element + 1) */
            for (int j = (i + 1); j < contactInformation.size(); j++) {

                /*
                    This method became necessary because we don't always have all the elements
                    in the CSV so if any of them (source or match) has null elements it will not be
                    considered
                */
                for (int k = 1; k < 6; k++) {
                    String contactSourceValue = contactInformation.get(i).length <= k ? " " : contactInformation.get(i)[k];

                    String contactMatchValue = contactInformation.get(j).length <= k ? " " : contactInformation.get(j)[k];

                    result += obtainPartialResult(contactSourceValue, contactMatchValue);
                }


                comparatorResult.add(new String[]{ contactInformation.get(i)[0],
                                                   contactInformation.get(j)[0],
                                                   String.valueOf(result),
                                                   defineAccuracy(result)});
                //Initialize again the result
                result = 0;

            }
        }
        return comparatorResult;
    }

    private double obtainPartialResult(String contactSourceValue, String contactMatchValue) {
        LevenshteinCalculator calculator = new LevenshteinCalculator();

        if(contactMatchValue.isEmpty())
            contactMatchValue = " ";

        if(contactSourceValue.isEmpty())
            contactSourceValue = " ";

        return calculator.getDifference(contactSourceValue,
                                        contactMatchValue);
    }

    private String defineAccuracy(double resultComparator) {
        if (resultComparator == 0.0) {
            return "Equal";
        } else if ( resultComparator >= 3.0) {
            return "Low";
        } else if (resultComparator > 2) {
            return "Medium";
        } else if (resultComparator <= 2) {
            return "High";
        }
        return "Error";
    }

}
