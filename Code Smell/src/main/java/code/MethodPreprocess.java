package code;

import java.io.FileNotFoundException;

public class MethodPreprocess {
    public static String process (String s) throws FileNotFoundException {
        TextPreprocess tp = new TextPreprocess();

        String javaKeywordRemover = tp.KeyWordRemover(s);
        String EnglishStopWordRemoved = tp.removeEnglishStopwords(javaKeywordRemover);
        String camelDone = tp.splitByCamelCase(EnglishStopWordRemoved);
        EnglishStopWordRemoved = tp.removeEnglishStopwords(camelDone);
        javaKeywordRemover = tp.KeyWordRemover(EnglishStopWordRemoved);
        //String stemmed = tp.stem(javaKeywordRemover);
        String underDone = tp.splitByUnderScore(javaKeywordRemover);
        underDone = tp.KeyWordRemover(underDone);
        EnglishStopWordRemoved = tp.removeEnglishStopwords(underDone);
        String specialCharacterRemoved = tp.removeSpecialCharacter(EnglishStopWordRemoved);
        javaKeywordRemover = tp.KeyWordRemover(specialCharacterRemoved);
        String spaceRemove = tp.spaceRemove(javaKeywordRemover);
        String stemmed = tp.stem(spaceRemove);

        String finalized = tp.splitByNumber(stemmed);

        return finalized;
    }
}
