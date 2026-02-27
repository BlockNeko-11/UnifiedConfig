package io.github.blockneko11.config.unified.exception;

import java.io.IOException;

public class ConfigIOException extends ConfigException {
    public ConfigIOException(String message, IOException cause) {
        super(message, cause);
    }

    public ConfigIOException(IOException cause) {
        super(cause);
    }
}
