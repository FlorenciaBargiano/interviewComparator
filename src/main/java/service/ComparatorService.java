package service;

import com.github.wslf.levenshteindistance.LevenshteinCalculator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ComparatorService {
    private static final String[] RESULT_HEADERS = new String[] { "ContactID Source", "ContactID Match", "Accuracy" };


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
        List<String[]> contactInformation = processDataService.obtainContactInformation();

        //Step 2
        List<String[]> contactInformationCompared = compareContactInformation(contactInformation);

        //Step 3
        processDataService.convertContactComparatorIntoACSVFile(contactInformationCompared);
    }

    private List<String[]> compareContactInformation(List<String[]> contactInformation) {

        //TODO determine if we use this dependency or create ours method
        LevenshteinCalculator calculator = new LevenshteinCalculator();
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
                int valor = returnMinor(contactInformation.get(i).length,
                                        contactInformation.get(j).length);

                for (int k = 1; k < valor; k++) {
                    double partialResult = calculator.getDifference(contactInformation.get(i)[k],
                                                                    contactInformation.get(j)[k]);

                    result += partialResult;
                }

                //TODO change the name comparatorResult
                comparatorResult.add(new String[]{ String.valueOf(i),
                                            String.valueOf(j),
                                            defineAccuracy(result)});
                //Initialize again the result
                result = 0;

            }
        }
        return comparatorResult;
    }

    private String defineAccuracy(double resultComparator) {
        if(resultComparator == 0){
            return "Equal";
        } else if (resultComparator > 3) {
            return "Low";
        }

        return "High";
    }

    private int returnMinor(int contactSourceSize, int contactMatchSize) {
        if (contactSourceSize == contactMatchSize || contactSourceSize < contactMatchSize)
            return contactSourceSize;

        return contactMatchSize;
    }

}
