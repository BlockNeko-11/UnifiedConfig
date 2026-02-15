package io.github.blockneko11.config.unified.util.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public final class IOUtils {
    public static String readFile(File file) {
        try (BufferedReader br = Files.newBufferedReader(file.toPath())) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            return sb.toString();
        } catch (IOException e) {
            throw new RuntimeException("cannot read from file " + file.getAbsolutePath(), e);
        }
    }

    public static void writeFile(File file, String content) {
        try (BufferedWriter bw = Files.newBufferedWriter(file.toPath())) {
            bw.write(content);
            bw.flush();
        } catch (IOException e) {
            throw new RuntimeException("cannot write to file " + file.getAbsolutePath(), e);
        }
    }
}
