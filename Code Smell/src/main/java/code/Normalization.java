package code;

import java.io.FileNotFoundException;

public interface Normalization {
    public String splitByCamelCase(String s);
    public String splitByUnderScore(String s);
    public String splitByNumber(String s);
    public String removeSpecialCharacter(String s);
    public String removeEnglishStopwords(String s) throws FileNotFoundException;

    public String KeyWordRemover(String str) throws FileNotFoundException;

    public String stem(String str) throws FileNotFoundException;

    public String spaceRemove(String str) throws FileNotFoundException;
}
