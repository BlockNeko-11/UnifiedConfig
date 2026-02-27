package io.github.blockneko11.config.unified.exception;

public class ConfigConversionException extends ConfigException {
    public ConfigConversionException() {
        super();
    }

    public ConfigConversionException(String message) {
        super(message);
    }

    public ConfigConversionException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfigConversionException(Throwable cause) {
        super(cause);
    }
}
