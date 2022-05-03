package learn.fileio;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) {



        List<String> lines = getTextFromFile("./data/README.txt");

        for (String line : lines) {


            // TODO only print the lines that are commands

            // CREATE
            // APPEND
            // DELETE
            // COPY

            System.out.println(line);


        }






    }

    // TODO consider moving this method to its own class

    private static List<String> getTextFromFile(String fileName) {
        List<String> lines = new ArrayList<>();

        try (FileReader fileReader = new FileReader(fileName);
             BufferedReader reader = new BufferedReader(fileReader)) {

            // FileReader - is doing the work of reading the file from the operating system's file system
            // BufferedReader - this is a "wrapper" around the file reader... it gives a set of convenience methods
            // for getting data from the file reader

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                lines.add(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return lines;
    }
}
