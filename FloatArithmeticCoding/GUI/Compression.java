import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.math.BigDecimal;
import java.math.MathContext;

public class Compression {
    public String compress(String str) {
        String input = str.replace("\r\n", "\n");

        TreeMap<Character, Integer> freq = new TreeMap<>();

        for (char c : input.toCharArray()) {
            freq.put(c, freq.getOrDefault(c, 0) + 1);
        }

        int totalFrequency = input.length();

        BigDecimal currentRangeStart = BigDecimal.ZERO;
        HashMap<Character, BigDecimal[]> ranges = new HashMap<>();

        for (Map.Entry<Character, Integer> entry : freq.entrySet()) {
            char c = entry.getKey();
            int frequency = entry.getValue();
            BigDecimal rangeEnd = currentRangeStart.add(new BigDecimal(frequency).divide(new BigDecimal(totalFrequency), MathContext.DECIMAL128));
            ranges.put(c, new BigDecimal[]{currentRangeStart, rangeEnd});
            currentRangeStart = rangeEnd;
        }

        BigDecimal lower = BigDecimal.ZERO;
        BigDecimal upper = BigDecimal.ONE;

        for (char c : input.toCharArray()) {
            BigDecimal newLower = lower.add(upper.subtract(lower).multiply(ranges.get(c)[0]));
            BigDecimal newUpper = lower.add(upper.subtract(lower).multiply(ranges.get(c)[1]));
            lower = newLower;
            upper = newUpper;
        }

        BigDecimal result = lower.add(upper).divide(new BigDecimal(2), MathContext.DECIMAL128);
        StringBuilder output = new StringBuilder();

        output.append(result.toString()).append("\n\n");

        output.append("Number of characters: ").append(input.length()).append("\n\n");

        for (Map.Entry<Character, Integer> entry : freq.entrySet()) {
            output.append(entry.getKey() == '\n' ? "\\n" : entry.getKey()).append(": ").append(new BigDecimal(entry.getValue()).divide(new BigDecimal(input.length()), MathContext.DECIMAL128)).append("\n");
        }

        if (output.lastIndexOf("\n") == output.length() - 1) {
            output.deleteCharAt(output.length() - 1);
        }

        return output.toString();
    }
}