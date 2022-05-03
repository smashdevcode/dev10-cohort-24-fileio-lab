package learn.fileio;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) {
        List<String> lines = getTextFromFile("./data/README.txt");

        for (String line : lines) {
            if (line.startsWith("CREATE")) {
                processCreateCommand(line);
            } else if (line.startsWith("APPEND")) {
                processAppendCommand(line);
            } else if (line.startsWith("DELETE")) {
                processDeleteCommand(line);
            } else if (line.startsWith("COPY")) {
                processCopyCommand(line);
            }
        }
    }

    private static void processCreateCommand(String line) {
        String[] parts = line.split(" ");
        String fileName = parts[1];

        File file = new File("temp/" + fileName);
        try {
            // TODO do we need to do something if the file already exists?
            file.mkdirs();
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    private static void processAppendCommand(String line) {
//        String[] parts = line.split(" ");
//        String fileName = parts[1];
//
//        try (FileWriter fileWriter = new FileWriter("data/" + fileName, true);
//            PrintWriter writer = new PrintWriter(fileWriter)) {
//            // HACK... sigh... this kind of sucks
//            for (int i = 2; i < parts.length; i++) {
//                writer.print(parts[i] + " ");
//            }
//            writer.println();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    private static void processAppendCommand(String line) {
        int firstSpaceIndex = line.indexOf(" ");
        int secondSpaceIndex = line.indexOf(" ", firstSpaceIndex + 1);

        String fileName = line.substring(firstSpaceIndex + 1, secondSpaceIndex);
        String value = line.substring(secondSpaceIndex + 1);

        try (FileWriter fileWriter = new FileWriter("data/" + fileName, true);
             PrintWriter writer = new PrintWriter(fileWriter)) {
            writer.println(value);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void processDeleteCommand(String line) {
        String[] parts = line.split(" ");
        String fileName = parts[1];

        File file = new File("data/" + fileName);
        file.delete();
    }

    private static void processCopyCommand(String line) {
        String[] parts = line.split(" ");
        String sourceFileName = parts[1];
        String destinationFileName = parts[2];

        Path sourcePath = Paths.get("data/" + sourceFileName);
        Path destinationPath = Paths.get("data/" + destinationFileName);

        try {
            // create the directory for the destination file
            Files.createDirectories(destinationPath.getParent());

            // get the contents of the source file
            String content = Files.readString(sourcePath);

            // write the destination file contents
            Files.writeString(destinationPath, content, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
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
