package io.github.blockneko11.config.unified.conversion;

import io.github.blockneko11.config.unified.exception.ConfigConversionException;

public interface ConfigConvertor<T> {
     Class<T> getTargetType();

    T toTarget(Object config) throws ConfigConversionException; // @NotNull S config

    Object toSerialized(T field) throws ConfigConversionException;
}
