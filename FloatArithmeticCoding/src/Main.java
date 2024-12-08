import java.io.IOException;
import java.util.Scanner;

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
        while (true) {
            System.out.print("Enter your choice: ");
            switch (choice) {
                case 1:
                    readWriteFiles.writeStringToFile(compression.compress(readWriteFiles.readStringFromFile("compress.txt")), "decompress.txt");
                    break;
                case 2:
                     readWriteFiles.writeStringToFile(decompression.decompress(readWriteFiles.readStringFromFile("decompress.txt")), "compress.txt");
                    break;
                case 3:
                    System.out.println("Exiting the program.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            System.out.println();
            choice = scanner.nextInt();
        }
    }
}
