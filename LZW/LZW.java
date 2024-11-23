import java.util.ArrayList;

public class LZW implements Compression, Decompression {
    @Override
    public ArrayList<String> compression(String content) {
        ArrayList<String> dictionary = new ArrayList<>();
        ArrayList<String> result = new ArrayList<>();
        for (int i = 0; i <= 127; i++) {
            dictionary.add(String.valueOf((char) i));
        }
        for (int i = 0; i < content.length(); i++) {
            int index = 0;
            for (int j = i; j < content.length(); j++) {
                String cur = content.substring(i, j + 1);
                boolean found = false;
                for (String s : dictionary) {
                    if (s.equals(cur)) {
                        index = dictionary.indexOf(s);
                        found = true;
                    }
                }
                if (!found || j == content.length() - 1) {
                    result.add(Integer.toString(index));
                    if (!found) {
                        dictionary.add(content.substring(i, j + 1));
                        i = j - 1;
                    }
                    else {
                        i = j;
                    }
                    break;
                }
            }
        }
        return result;
    }

    @Override
    public String decompression(ArrayList<String> content) {
        ArrayList<String> dictionary = new ArrayList<>();
        String result = "";
        for (int i = 0; i <= 127; i++) {
            dictionary.add(String.valueOf((char) i));
        }
        String prev = "";
        if (!content.isEmpty()) {
            prev = dictionary.get(Integer.parseInt(content.getFirst()));
            result = prev;
        }
        for (int i = 1; i < content.size(); i++) {
            if (Integer.parseInt(content.get(i)) < dictionary.size()) {
                String cur = dictionary.get(Integer.parseInt(content.get(i)));
                result += cur;
                dictionary.add(prev + cur.charAt(0));
                prev = cur;
            }
            else {
                dictionary.add(prev + prev.charAt(0));
                String cur = dictionary.get(Integer.parseInt(content.get(i)));
                result += cur;
                prev = cur;
            }
        }
        return result;
    }
}
