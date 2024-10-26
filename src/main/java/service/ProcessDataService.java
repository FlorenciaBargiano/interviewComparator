package service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ProcessDataService {

    private static final String COMMA_SEPARATOR = ",";
    private static final String BASE_SOURCE = "./src/main/resources/files";
    private static final String INPUT_SOURCE = "/duplicates.csv";
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
            //TODO add a specific error
            System.out.println("An error happen when trying to read the file");
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
            //TODO add a specific error
            System.out.println("The file was not found: " + e);
        }
    }

}
