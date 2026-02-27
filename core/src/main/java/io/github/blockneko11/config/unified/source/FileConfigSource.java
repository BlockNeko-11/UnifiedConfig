package io.github.blockneko11.config.unified.source;

import io.github.blockneko11.config.unified.exception.ConfigException;
import io.github.blockneko11.config.unified.exception.ConfigIOException;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

public class FileConfigSource implements ConfigSource {
    private final File file;

    public FileConfigSource(File file) {
        this.file = file;
    }

    @Override
    public String load() throws ConfigException {
        try {
            List<String> lines = Files.readAllLines(this.file.toPath(), StandardCharsets.UTF_8);
            return String.join("\n", lines);
        } catch (IOException e) {
            throw new ConfigIOException(e);
        }
    }

    @Override
    public void save(String config) throws ConfigException {
        try {
            Files.write(this.file.toPath(), config.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new ConfigIOException(e);
        }
    }
}
