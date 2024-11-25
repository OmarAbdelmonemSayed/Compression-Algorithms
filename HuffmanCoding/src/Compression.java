import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Compression {
    // DFS
    public static HashMap<Character, String> generateCodes(HuffmanNode root, String currentCode) {
        HashMap<Character, String> codes = new HashMap<>();

        if (root.left == null && root.right == null) {
            codes.put(root.c, currentCode);
            return codes;
        }

        HashMap<Character, String> leftCodes = generateCodes(root.left, currentCode + "0");
        HashMap<Character, String> rightCodes = generateCodes(root.right, currentCode + "1");

        codes.putAll(leftCodes);
        codes.putAll(rightCodes);

        return codes;
    }

    public String compress(String str) {
        String input = str.replace("\r\n", "\n");
        PriorityQueue<HuffmanNode> pq = new PriorityQueue<>((a, b) -> Double.compare(a.probability, b.probability));
        HashMap<Character, Integer> freq = new HashMap<>();

        // Calculate the frequency of each character
        for (char c : input.toCharArray()) {
            freq.put(c, freq.getOrDefault(c, 0) + 1);
        }

        // Add the Huffman Nodes in the priorityQueue
        for (Map.Entry<Character, Integer> entry : freq.entrySet()) {
            char x = entry.getKey();
            double y = entry.getValue();
            HuffmanNode huffmanNode = new HuffmanNode(x, y / input.length());
            pq.add(huffmanNode);
        }

        // Build the Huffman tree
        while (pq.size() > 1) {
            HuffmanNode huffmanNode1 = pq.poll();
            HuffmanNode huffmanNode2 = pq.poll();
            HuffmanNode huffmanNode = new HuffmanNode(' ', huffmanNode1.probability + huffmanNode2.probability);
            huffmanNode.left = huffmanNode1;
            huffmanNode.right = huffmanNode2;
            pq.add(huffmanNode);
        }

        HashMap<Character, String> result = new HashMap<>();

        if (!pq.isEmpty()) result = generateCodes(pq.poll(), "");

        String compressedCode = "";
        for (int i = 0; i < input.length(); i++) {
            compressedCode += result.get(input.charAt(i));
        }

        compressedCode += "\n\n";

        // Add the Huffman codes
        for (Map.Entry<Character, String> entry : result.entrySet()) {
            char key = entry.getKey();
            String value = entry.getValue();
            if (key == '\n') {
                compressedCode += "\\n: " + value + "\n";
            } else {
                compressedCode += key + ": " + value + "\n";
            }
        }

        if (!compressedCode.isEmpty() && compressedCode.charAt(compressedCode.length() - 1) == '\n') {
            compressedCode = compressedCode.substring(0, compressedCode.length() - 1);
        }
        
        return compressedCode;
    }
}
