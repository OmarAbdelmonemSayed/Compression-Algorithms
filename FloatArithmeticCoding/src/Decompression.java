import com.sun.source.tree.Tree;

import java.util.TreeMap;
import java.util.Map;
import java.util.HashMap;

public class Decompression {
    public String decompress(String input) {
        String[] parts = input.split("\n");

        double encodedValue = Double.parseDouble(parts[0]);

        int originalLength = 0;
        for (String part : parts) {
            if (part.startsWith("Number of characters:")) {
                originalLength = Integer.parseInt(part.split(":")[1].trim());
                break;
            }
        }

        TreeMap<Character, double[]> ranges = new TreeMap<>();
        double currentRangeStart = 0.0;

        for (int i = 1; i < parts.length; i++) {

            if (parts[i].startsWith("Number of characters:")) continue;

            if (parts[i].trim().isEmpty()) continue;

            String[] freqParts = parts[i].split(": ");
            if (freqParts.length != 2) continue;

            char character = (freqParts[0].equals("\\n") ? '\n' : freqParts[0].charAt(0));
            double frequency = Double.parseDouble(freqParts[1]);

            double rangeEnd = currentRangeStart + frequency;
            ranges.put(character, new double[]{currentRangeStart, rangeEnd});
            currentRangeStart = rangeEnd;
        }

        StringBuilder decompressedString = new StringBuilder();
        double lower = 0.0;
        double upper = 1.0;

//        double epsilon = 1e-25;

        for (int i = 0; i < originalLength; i++) {

//            double currentRange;
//            double tempRange = upper - lower;
//            if (Math.abs(tempRange) > epsilon) {
//                currentRange  = (encodedValue - lower) / tempRange;
//            }
//            else {
//                currentRange = encodedValue - lower;
//            }

            double currentRange = (encodedValue - lower) / (upper - lower);
            for (Map.Entry<Character, double[]> entry : ranges.entrySet()) {
                double[] range = entry.getValue();
                if (currentRange >= range[0] && currentRange < range[1]) {
                    decompressedString.append(entry.getKey());
                    double newLower = lower + (upper - lower) * range[0];
                    double newUpper = lower + (upper - lower) * range[1];
                    lower = newLower;
                    upper = newUpper;
                    break;
                }
            }

        }


        return decompressedString.toString();
    }
}
