package code;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class FileInfoRetrieve {
    public static List<String> javaFiles = new ArrayList<>();
    public static void listFilesForFolder(File destDir) throws FileNotFoundException {

        Scanner sc = null;

        File filesList[] = destDir.listFiles();
        //System.out.println("List of files and directories in the specified directory:");
        //Scanner sc = null;
        for(File file : filesList) {
            System.out.println("File name: "+file.getName());
            String fileName = file.getName();
            //System.out.println("File path: "+file.getAbsolutePath());
            String tempFileName = file.getAbsolutePath();
            String tempFileContent;

            //javaFiles.add(tempFileName + System.lineSeparator() +  tempFileContent);
            //System.out.println("Size :"+file.getTotalSpace());
            //Instantiating the Scanner class
            sc= new Scanner(file);
            String input;
            StringBuffer sb = new StringBuffer();
            while (sc.hasNextLine()) {
                input = sc.nextLine();
                sb.append(input+" ");
            }
            tempFileContent = sb.toString();
            //javaFiles.add(tempFileName + System.lineSeparator() +  tempFileContent);
            //System.out.println();

            //printArraylist();

            methodExtraction(tempFileName, fileName);
        }
    }

    private static void methodExtraction(String tempFileName, String fileName) {
        try {
            Scanner in = new Scanner(new File(tempFileName));
            String classContents = in.useDelimiter("\\Z").next().trim();
            String classElements = "";
            JavaMethodParser jmp = new JavaMethodParser(classContents);

            while(jmp.hasMoreMethods()) {
                //System.out.println("name: " + jmp.getMethodName());
                System.out.println(jmp.getMethod());
                classElements = jmp.getMethod() + classElements;
                //System.out.println();
            }
            writeClassToFile(classElements, fileName);

            in.close();

        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void writeClassToFile(String method, String  Name) throws IOException {
        String path = "E:\\Semester 8\\SPL\\tempt\\ATM-Machine-master\\ATM-Machine-master\\ATM-Machine-master\\ATM\\tf\\";
        File file = new File(path+  Name + ".txt");
        file.getParentFile().mkdirs();
        BufferedWriter writer = new BufferedWriter(new FileWriter(path +  Name + ".txt"));
        writer.write(method);
        TfIdfHandle tfIdf = new TfIdfHandle();
        tfIdf.TfIdfHandler(path, Name);

        writer.close();
    }

    public static void printArraylist() {

        System.out.println(Arrays.toString(javaFiles.toArray()));
    }
}
