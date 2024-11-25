import java.io.IOException;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) throws IOException {
        ReadWriteFiles readWriteFiles = new ReadWriteFiles();
        Compression compression = new Compression();
        Decompression decompression = new Decompression();
        Scanner scanner = new Scanner(System.in);
        int choice;
        System.out.println("Menu:");
        System.out.println("1. Compression");
        System.out.println("2. Decompression");
        System.out.println("3. Exit");
        System.out.print("Enter your choice: ");

        choice = scanner.nextInt();

        switch (choice) {
            case 1:
                readWriteFiles.writeStringToFile(compression.compress(readWriteFiles.readStringFromFile("src\\compress.txt")), "src\\decompress.txt");
                break;
            case 2:
                readWriteFiles.writeStringToFile(decompression.decompress("src\\decompress.txt"), "src\\compress.txt");
                break;
            case 3:
                System.out.println("Exiting the program.");
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
        System.out.println();

        scanner.close();
    }
}