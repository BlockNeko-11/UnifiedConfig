package io.github.blockneko11.config.unified.impl.source;

import io.github.blockneko11.config.unified.api.source.FileConfigSource;
import io.github.blockneko11.config.unified.exception.ConfigException;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileConfigSourceImpl implements FileConfigSource {
    private final Path file;

    public FileConfigSourceImpl(String file) {
        this(new File(file));
    }

    public FileConfigSourceImpl(File file) {
        this(file.toPath());
    }

    public FileConfigSourceImpl(Path file) {
        this.file = file;
    }

    @Override
    public Path getFile() {
        return this.file;
    }

    @Override
    public String load() throws ConfigException {
        try {
            List<String> lines = Files.readAllLines(this.getFile(), StandardCharsets.UTF_8);
            return String.join("\n", lines);
        } catch (IOException e) {
            throw new ConfigException(e);
        }
    }

    @Override
    public void save(String config) throws ConfigException {
        try {
            Files.write(this.getFile(), config.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new ConfigException(e);
        }
    }
}
