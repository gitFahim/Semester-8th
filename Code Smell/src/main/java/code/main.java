package code;


import java.io.File;
import java.io.IOException;

public class main {
    public static void main(String[] args) throws IOException {
        ZipHandler zipp = new ZipHandler();
        FileInfoRetrieve infoRetrieve = new FileInfoRetrieve();
        File destDir = new File("E:\\Semester 8\\SPL\\tempt\\ATM-Machine-master\\ATM-Machine-master\\ATM-Machine-master\\ATM");
        //zipp.ZipFileContent("E:/Semester 8/SPL/tempt/ATM-Machine-master.zip", destDir);
        FileInfoRetrieve.listFilesForFolder(destDir);
    }
}
