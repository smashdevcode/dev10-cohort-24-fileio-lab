package learn.fileio;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class App {
    public static void main(String[] args) {




        // TODO open and read the contents of the README.txt file

        // TODO print each line to the console


        try (FileReader fileReader = new FileReader("./data/README.txt");
             BufferedReader reader = new BufferedReader(fileReader)) {

            // FileReader - is doing the work of reading the file from the operating system's file system
            // BufferedReader - this is a "wrapper" around the file reader... it gives a set of convenience methods
            // for getting data from the file reader

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                System.out.println(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        System.out.println("Hello world!");
    }
}
