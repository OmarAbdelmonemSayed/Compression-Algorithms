
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Compression {
    public String compress(String str) {
        String input = str.replace("\r\n", "\n");

        TreeMap<Character, Integer> freq = new TreeMap<>();

        for (char c : input.toCharArray()) {
            freq.put(c, freq.getOrDefault(c, 0) + 1);
        }

        int totalFrequency = input.length();

        double currentRangeStart = 0.0;
        HashMap<Character, double[]> ranges = new HashMap<>();
        
        for (Map.Entry<Character, Integer> entry : freq.entrySet()) {
            char c = entry.getKey();
            int frequency = entry.getValue();
            
            double rangeEnd = currentRangeStart + (double) frequency / totalFrequency;
            ranges.put(c, new double[]{currentRangeStart, rangeEnd});
            currentRangeStart = rangeEnd;
        }

        double lower = 0.0;
        double upper = 1.0;

        for (char c : input.toCharArray()) {
            double newLower = lower + (upper - lower) * ranges.get(c)[0];
            double newUpper = lower + (upper - lower) * ranges.get(c)[1];
            lower = newLower;
            upper = newUpper;
        }

        double result = (lower + upper) / 2;
        StringBuilder output = new StringBuilder();

        output.append(result).append("\n\n");

        output.append("Number of characters: ").append(input.length()).append("\n\n");

        for (Map.Entry<Character, Integer> entry : freq.entrySet()) {
            output.append(entry.getKey() == '\n' ? "\\n" : entry.getKey()).append(": ").append((double) entry.getValue() / input.length()).append("\n");
        }

        if (output.lastIndexOf("\n") == output.length() - 1) {
            output.deleteCharAt(output.length() - 1);
        }

        return output.toString();
    }
}
