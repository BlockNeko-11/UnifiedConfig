package io.github.blockneko11.config.unified.exception;

public class ConfigSerializationException extends ConfigException {
    public ConfigSerializationException() {
        super();
    }

    public ConfigSerializationException(String message) {
        super(message);
    }

    public ConfigSerializationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfigSerializationException(Throwable cause) {
        super(cause);
    }
}
