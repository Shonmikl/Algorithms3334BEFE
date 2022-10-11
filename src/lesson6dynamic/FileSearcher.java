package lesson6dynamic;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileSearcher {
    public static void main(String[] args) {
        ArrayList<File> fileList = new ArrayList<>();
        getFiles(new File("E:\\"), fileList);
        for (File file : fileList) {
            System.out.println(file.getAbsolutePath());
        }
    }

    private static void getFiles(File rootFile, List<File> fileList) {
        if (rootFile.isDirectory()) {
            System.out.println("searching...." + rootFile.getAbsolutePath());
            File[] directoryFiles = rootFile.listFiles();
            if (directoryFiles != null) {
                for (File file : directoryFiles) {
                    if (file.isDirectory()) {
                        getFiles(file, fileList);
                    } else {
                        if (file.getName().toLowerCase().endsWith(".mp3")) {
                            fileList.add(file);
                        }
                    }
                }
            }
        }
    }
}