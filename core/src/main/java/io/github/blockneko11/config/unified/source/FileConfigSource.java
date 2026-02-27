package io.github.blockneko11.config.unified.source;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

public class FileConfigSource implements ConfigSource {
    private final File file;

    public FileConfigSource(File file) {
        this.file = file;
    }

    @Override
    public String load() throws Exception {
        List<String> lines = Files.readAllLines(this.file.toPath(), StandardCharsets.UTF_8);
        return String.join("\n", lines);
    }

    @Override
    public void save(String config) throws Exception {
        Files.write(this.file.toPath(), config.getBytes(StandardCharsets.UTF_8));
    }
}
