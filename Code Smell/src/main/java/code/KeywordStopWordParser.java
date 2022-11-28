package code;

import java.io.*;
import java.util.ArrayList;

public class KeywordStopWordParser {

    public static String WordFinder(String str, String source) {
        int k = 0;
        ArrayList<String> wordsList = new ArrayList<String>();
        String sCurrentLine;
        String[] stopwords = new String[851];
        try {
            FileReader fr = new FileReader(source);
            BufferedReader br = new BufferedReader(fr);
            while ((sCurrentLine = br.readLine()) != null) {
                stopwords[k] = sCurrentLine;
                k++;
            }
            //String str = "I love this phone, its super fast and there's so much new and cool things with jelly bean....but of recently I've seen some bugs.";
            str.trim().replaceAll(" +", " ");
            StringBuilder builder = new StringBuilder(str);

            String[] words = builder.toString().split("\\s");
            for (String word : words) {
                wordsList.add(word);
            }
            for (int ii = 0; ii < wordsList.size(); ii++) {
                for (int jj = 0; jj < k; jj++) {
                    if (stopwords[jj].contains(wordsList.get(ii).toLowerCase())) {
                        wordsList.remove(ii);
                        break;
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }

        String listString = String.join(" ", wordsList);

        return listString;
    }
    public static String StopWordParcer(String str) throws FileNotFoundException {
        String source = "src/main/res/stop_words_english.txt";
        return WordFinder(str, source);
    }
//"src/main/res/java_keywords.txt"

    public static String KeyWordRemover(String str) throws FileNotFoundException {
        String source = "src/main/res/java_keywords.txt";
        return WordFinder(str, source);
    }
}
