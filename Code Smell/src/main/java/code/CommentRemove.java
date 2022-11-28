package code;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CommentRemove {
    public static String commentRemover(String method) {
        String str = Stream.of(method.split("\n"))
                .filter(s -> !s.contains("//"))
                .collect(Collectors.joining("\n"));
        return str;
    }
}
