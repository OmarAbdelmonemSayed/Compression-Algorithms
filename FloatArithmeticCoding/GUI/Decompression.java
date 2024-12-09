import com.sun.source.tree.Tree;

import java.util.TreeMap;
import java.util.Map;
import java.util.HashMap;
import java.math.BigDecimal;
import java.math.MathContext;

public class Decompression {
    public String decompress(String input) {
        String[] parts = input.split("\n");

        BigDecimal encodedValue = new BigDecimal(parts[0]);

        int originalLength = 0;
        for (String part : parts) {
            if (part.startsWith("Number of characters:")) {
                originalLength = Integer.parseInt(part.split(":")[1].trim());
                break;
            }
        }

        TreeMap<Character, BigDecimal[]> ranges = new TreeMap<>();
        BigDecimal currentRangeStart = BigDecimal.ZERO;

        for (int i = 1; i < parts.length; i++) {
            if (parts[i].startsWith("Number of characters:")) continue;

            if (parts[i].trim().isEmpty()) continue;

            String[] freqParts = parts[i].split(": ");
            if (freqParts.length != 2) continue;

            char character = (freqParts[0].equals("\\n") ? '\n' : freqParts[0].charAt(0));
            BigDecimal frequency = new BigDecimal(freqParts[1]);

            BigDecimal rangeEnd = currentRangeStart.add(frequency);
            ranges.put(character, new BigDecimal[]{currentRangeStart, rangeEnd});
            currentRangeStart = rangeEnd;
        }

        StringBuilder decompressedString = new StringBuilder();
        BigDecimal lower = BigDecimal.ZERO;
        BigDecimal upper = BigDecimal.ONE;

        for (int i = 0; i < originalLength; i++) {
            BigDecimal currentRange = (encodedValue.subtract(lower))
                    .divide(upper.subtract(lower), MathContext.DECIMAL128);

            for (Map.Entry<Character, BigDecimal[]> entry : ranges.entrySet()) {
                BigDecimal[] range = entry.getValue();
                BigDecimal rangeStart = range[0];
                BigDecimal rangeEnd = range[1];

                if (currentRange.compareTo(rangeStart) >= 0 && currentRange.compareTo(rangeEnd) < 0) {
                    decompressedString.append(entry.getKey());
                    BigDecimal newLower = lower.add(upper.subtract(lower).multiply(rangeStart));
                    BigDecimal newUpper = lower.add(upper.subtract(lower).multiply(rangeEnd));
                    lower = newLower;
                    upper = newUpper;
                    break;
                }
            }
        }
        return decompressedString.toString();
    }
}
