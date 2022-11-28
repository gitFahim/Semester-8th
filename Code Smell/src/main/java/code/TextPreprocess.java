package code;

import java.io.FileNotFoundException;

public class TextPreprocess implements Normalization {
    @Override
    public String splitByCamelCase(String s) {
        return s.replaceAll(
                String.format("%s|%s|%s",
                        "(?<=[A-Z])(?=[A-Z][a-z])",
                        "(?<=[^A-Z])(?=[A-Z])",
                        "(?<=[A-Za-z])(?=[^A-Za-z])"
                ),
                " "
        );
    }

    @Override
    public String splitByUnderScore(String s) {
        String[] parts = s.split("_");
        String camelCaseString = " ";
        for (String part : parts) {
            camelCaseString = camelCaseString + toProperCase(part);
        }
        return camelCaseString;
    }

    static String toProperCase(String s) {
        return s.substring(0, 1).toUpperCase() +
                s.substring(1).toLowerCase();
    }

    @Override
    public String splitByNumber(String s) {
        return s.replaceAll(
                String.format("(?<=\\D)(?=\\d)"
                ),
                " "
        );
    }

    @Override
    public String removeSpecialCharacter(String s) {
        return s.replaceAll("[^A-Za-z0-9]", " ");
    }

    @Override
    public String removeEnglishStopwords(String s) throws FileNotFoundException {

        KeywordStopWordParser ksp = new KeywordStopWordParser();

        return ksp.StopWordParcer(s);
    }

    @Override
    public String KeyWordRemover(String str) throws FileNotFoundException {
        KeywordStopWordParser ksp = new KeywordStopWordParser();

        return ksp.KeyWordRemover(str);
    }

    @Override
    public String stem(String str) throws FileNotFoundException {
        PorterStemmer ps = new PorterStemmer();
        String[] split = str.split("\\s+");
        String burn = "";
        for (int i= 0; i < split.length; i++) {
                burn = burn + ps.stemWord(split[i]) +  " ";
        }

        return ps.stemWord(burn);

    }

    @Override
    public String spaceRemove(String str) throws FileNotFoundException {
        return str.trim().replaceAll(" +", " ");
    }

}
