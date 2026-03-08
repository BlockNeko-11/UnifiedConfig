package io.github.blockneko11.config.unified.api.source;

import io.github.blockneko11.config.unified.impl.source.FileConfigSourceImpl;

import java.io.File;
import java.nio.file.Path;

public interface FileConfigSource extends ConfigSource {
    static FileConfigSource of(String file) {
        return new FileConfigSourceImpl(file);
    }

    static FileConfigSource of(File file) {
        return new FileConfigSourceImpl(file);
    }

    static FileConfigSource of(Path file) {
        return new FileConfigSourceImpl(file);
    }

    Path getFile();
}
