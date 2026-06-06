package io.github.blockneko11.config.unified.api.source;

import io.github.blockneko11.config.unified.impl.source.FileConfigSource;

import java.io.File;
import java.nio.file.Path;

public interface IFileConfigSource extends IConfigSource {
    static IFileConfigSource of(String file) {
        return new FileConfigSource(file);
    }

    static IFileConfigSource of(File file) {
        return new FileConfigSource(file);
    }

    static IFileConfigSource of(Path file) {
        return new FileConfigSource(file);
    }

    Path getFile();
}
