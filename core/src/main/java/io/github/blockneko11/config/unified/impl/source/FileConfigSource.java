package io.github.blockneko11.config.unified.impl.source;

import io.github.blockneko11.config.unified.api.source.IFileConfigSource;
import io.github.blockneko11.config.unified.exception.ConfigException;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileConfigSource implements IFileConfigSource {
    private final Path file;

    public FileConfigSource(String file) {
        this(new File(file));
    }

    public FileConfigSource(File file) {
        this(file.toPath());
    }

    public FileConfigSource(Path file) {
        this.file = file;
    }

    @Override
    public Path getFile() {
        return this.file;
    }

    @Nullable
    @Override
    public String load() throws ConfigException {
        if (!Files.exists(this.getFile())) {
            return null;
        }

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
            Files.createDirectories(this.getFile().getParent());
            Files.write(this.getFile(), config.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new ConfigException(e);
        }
    }
}
