package com.eavmusic.eavmusic.Helper;

import java.io.File;
import java.nio.file.Path;
import java.util.LinkedList;

public class FileUtil {
    public static boolean isMp3File(Path filePath) {
        String fileExtension = getFileExtension(filePath);
        return fileExtension.equalsIgnoreCase("mp3");
    }

    private static String getFileExtension(Path filePath) {
        String fileName = filePath.getFileName().toString();
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }

    public static void searchMp3Files(File[] files, LinkedList<File> musicContainer) {
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    searchMp3Files(file.listFiles(), musicContainer);
                } else if (isMp3File(file.toPath())) {
                    musicContainer.add(file);
                }
            }
        }
    }
}
