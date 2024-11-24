import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Decompression {
    public String decompress(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));

        StringBuilder binaryString = new StringBuilder();
        String line;

        // Read binary string until the empty line
        while ((line = reader.readLine()) != null && !line.trim().isEmpty()) {
            binaryString.append(line.trim());
        }

        // Read the Huffman table
        HashMap<String, Character> huffmanCodes = new HashMap<>();
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(": ");
            if (parts.length == 2) {
                char c = parts[0].equals("\\n") ? '\n' : parts[0].charAt(0);  // Character
                String code = parts[1]; // Huffman code
                huffmanCodes.put(code, c);
            }
        }
        reader.close();

        StringBuilder decodedString = new StringBuilder();
        StringBuilder currentCode = new StringBuilder();

        for (int i = 0; i < binaryString.length(); i++) {
            currentCode.append(binaryString.charAt(i));
            if (huffmanCodes.containsKey(currentCode.toString())) {
                decodedString.append(huffmanCodes.get(currentCode.toString()));
                currentCode.setLength(0);
            }
        }
        return decodedString.toString();
    }
}
