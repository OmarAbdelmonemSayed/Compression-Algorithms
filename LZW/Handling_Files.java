import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Handling_Files {
    public static ArrayList<String> readArrayFromFile(String fileName) {
        File file = new File(fileName);
        try {
            List<String> input = Files.readAllLines(file.toPath());
            return new ArrayList<>(input);
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public static String readStringFromFile(String fileName) {
        File file = new File(fileName);
        try {
            return Files.readString(file.toPath());
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
            return null;
        }
    }
    public static void writeStringToFile(String content, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            if (content != null && !content.isEmpty()) {
                writer.write(content);
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    public static void writeArrayToFile(ArrayList<String> content, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (int i = 0; i < content.size(); i++) {
                writer.write(content.get(i));
                if (i < content.size() - 1) {
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}