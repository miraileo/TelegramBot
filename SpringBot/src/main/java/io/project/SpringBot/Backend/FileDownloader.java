package io.project.SpringBot.Backend;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class FileDownloader {

    public static File downloadFile(String fileUrl, String localFileName) throws IOException {
        File outputFile = new File(localFileName);

        try (InputStream in = new URL(fileUrl).openStream();
             FileOutputStream fos = new FileOutputStream(outputFile)) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
        }

        return outputFile;
    }
}
