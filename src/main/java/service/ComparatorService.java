package service;

import com.github.wslf.levenshteindistance.LevenshteinCalculator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@AllArgsConstructor
public class ComparatorService {

    public static void convertFileIntoList() {
        //TODO the logic of getting data has to be in a different service
        String filePath = "./src/main/resources/files/duplicates.csv";
        List<String[]> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                //The delimiter in the file used between words is a comma
                String[] values = line.split(",");
                data.add(values);
            }
        } catch (IOException e) {
            System.out.println("An error happen when trying to read the file");
        }

        compareStrings(data);
    }

    public static void compareStrings(List<String[]> data) {
        DecimalFormat df = new DecimalFormat("###.000", new DecimalFormatSymbols(Locale.US));
        LevenshteinCalculator calculator = new LevenshteinCalculator();
        double contador = 0;
        List<String[]> dataLines = new ArrayList<>();
        dataLines.add(new String[]
                { "Id1", "Id2", "Result", "Decision" });

        //TODO borrar
        //TODO ver que tenemos contador, resultado
        //dataLines.add(new String[]
           //     { "John", "Doe", "38", "Comment Data\nAnother line of comment data" });

        for (int i = 1; i < data.size(); i++) {
            for (int j = i + 1; j < data.size(); j++) {
                int valor = returnMinor(data.get(i).length, data.get(j).length);
                for (int k = 1; k < valor; k++) {
                    double resultado = calculator.getDifference(data.get(i)[k], data.get(j)[k]);

                    contador = resultado + contador;

                    dataLines.add(new String[]
                               { data.get(i)[0], data.get(j)[0], String.valueOf(df.format(contador)), defineDecision(contador) });
                }

                contador = 0;

            }
        }
        convertingToCSV(dataLines);
    }

    //TODO apply switch?
    private static String defineDecision(double contador) {
        if(contador == 0){
            return "Equal";
        } else if (contador>3) {
            return "Distinct";
        }

        return "Similar";

    }


    public static int returnMinor(int value1, int value2) {
        if (value1 == value2 || value1 < value2)
            return value1;

        return value2;
    }

    //TODO rename
    public static void convertingToCSV(List<String[]>  dataLines) {
        File csvOutputFile = new File("./src/main/resources/files/resultado.csv");
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            dataLines.stream()
                    .map(ComparatorService::convertToCSV)
                    .forEach(pw::println);
        } catch (FileNotFoundException e){
            System.out.println("The file was not found: " + e);
        }
    }

    public static String convertToCSV(String[] data) {
        return String.join(",", data);
    }


}
