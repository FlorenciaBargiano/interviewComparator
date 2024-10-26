package com.compass.excercise.comparator.service;

import com.compass.excercise.comparator.error.ErrorDetail;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ProcessDataService {

    private static final String COMMA_SEPARATOR = ",";
    private static final String BASE_SOURCE = "./src/main/resources/files";
    private static final String INPUT_SOURCE = "/files/duplicates.csv";
    private static final String OUTPUT_SOURCE = "/contactComparatorResult.csv";


    public List<String[]> obtainContactInformation() {
        String fileLocation = BASE_SOURCE+INPUT_SOURCE;
        List<String[]> contactsInformation = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileLocation))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] contactInformation = line.split(COMMA_SEPARATOR);
                contactsInformation.add(contactInformation);
            }
        } catch (IOException e) {
            String errorDescription = "An error happened when trying to read from the file";
            System.out.println(errorDescription);
            throw new ErrorDetail(LocalDateTime.now(), "ERROR_READING_FILE", errorDescription);
        }
        return contactsInformation;
    }


    public void convertContactComparatorIntoACSVFile(List<String[]>  dataLines) {
        String fileLocation = BASE_SOURCE+OUTPUT_SOURCE;

        File csvOutputFile = new File(fileLocation);
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            dataLines.stream()
                    .map(data -> String.join(COMMA_SEPARATOR, data))
                    .forEach(pw::println);
        } catch (FileNotFoundException e){
            String errorDescription = "The output file was not found ";
            System.out.println(errorDescription);
            throw new ErrorDetail(LocalDateTime.now(), "ERROR_SAVING_FILE", errorDescription);
        }
    }

}
