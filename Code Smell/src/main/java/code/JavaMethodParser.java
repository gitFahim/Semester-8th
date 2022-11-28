package code;

import java.io.*;
import java.lang.reflect.Array;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class JavaMethodParser {
    private List<String> methodList;
    private List<String> methodNameList;
    private List<String> wordsList;
    private List<String> commentList;
    private int cnt;
    private int cmt;
    public JavaMethodParser(String classContents) throws IOException {
        Pattern classPattern = Pattern.compile("[a-zA-Z]*\\s*class\\s+([_a-zA-Z]+)\\s*\\{(.*)}$", Pattern.DOTALL);

        Matcher classMatcher = classPattern.matcher(classContents);

        int i = 0;
        if(classMatcher.find()) {
            String methodContents = classMatcher.group(2);


            Pattern methodPattern = Pattern.compile("(public|protected|private|static|\\s) +[\\w\\<\\>\\[\\]]+\\s+(\\w+) *\\([^\\)]*\\) *(\\{?|[^;])", Pattern.DOTALL);
            Pattern commentPattern = Pattern.compile("/\\*([^*]|[\\r\\n]|(\\*+([^*/]|[\\r\\n])))*\\*+/");

            Matcher methodMatcher = methodPattern.matcher(methodContents);
            Matcher commentMatcher = commentPattern.matcher(classContents);

            List<String> methodStartList = new ArrayList<>();


            methodList = new ArrayList<>();
            wordsList = new ArrayList<>();
            methodNameList = new ArrayList<>();
            commentList = new ArrayList<>();

            while(methodMatcher.find()) {
                String methodName = methodMatcher.group(2);
                String methodStart = methodMatcher.group();
                methodStartList.add(methodStart);
                //methodNameList.add(methodName);
            }


            //TextPreprocess tp = new TextPreprocess();
            MethodPreprocess mp = new MethodPreprocess();

            Collections.reverse(methodStartList);
            Collections.reverse(methodNameList);
            //Collections.reverse(commentList);
            //System.out.println(Arrays.toString(commentList.toArray()));
            String buf = methodContents;

            for(String methodStart: methodStartList) {

                    String[] t = buf.split(Pattern.quote(methodStart));

                    String method = methodStart + t[1];
                    String unprocessedMethod = null, stemmed = null;
                    //String comments;
                    //findComments(method);
                    //comments = CommentRemove.commentCollector(method);
                    unprocessedMethod = CommentRemove.commentRemover(method);
                    //stemmed = ps.stem(unprocessedMethod);
                    method =  mp.process(unprocessedMethod);

//                    tfHandler(method);
//                    ArrayList<Object> tempMethodList = new ArrayList<>();
//                    tempMethodList.add(method.toLowerCase());

                    methodList.add(method.toLowerCase());
                    //commentList.add(comments);
                    buf = t[0];

            }

            //writeClassToFile(methodList.toString(), i);

        }

        else {
            System.out.println("error: class not found");

        }

        cnt = -1;

    }


    public boolean hasMoreMethods() {
        cnt += 1;
        return cnt < methodList.size();
    }
    public boolean hasMoreComments() {
        cnt += 1;
        return cnt < commentList.size();
    }

    public String getMethodName() {
        return methodNameList.get(cnt);
    }
    public String getMethod() {
        return methodList.get(cnt);
    }
    public String getComments() {
        return commentList.get(cnt);
    }

    public int countMethods() {
        return methodList.size();
    }
}
