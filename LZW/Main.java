import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Handling_Files files = new Handling_Files();
        LZW lzw = new LZW();
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("Menu:");
            System.out.println("1. Compression");
            System.out.println("2. Decompression");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    files.writeArrayToFile(lzw.compression(files.readStringFromFile("Compression.txt")), "Decompression.txt");
                    break;
                case 2:
                    files.writeStringToFile(lzw.decompression(files.readArrayFromFile("Decompression.txt")), "Compression.txt");
                    break;
                case 3:
                    System.out.println("Exiting the program.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

            System.out.println();
        } while (choice != 3);

        scanner.close();
    }
}