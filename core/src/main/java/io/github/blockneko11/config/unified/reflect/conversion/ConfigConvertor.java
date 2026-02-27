package io.github.blockneko11.config.unified.reflect.conversion;

import io.github.blockneko11.config.unified.exception.ConversionException;

public interface ConfigConvertor<T> {
     Class<T> getTargetType();

    T toTarget(Object config) throws ConversionException; // @NotNull S config

    Object toSerialized(T field) throws ConversionException;
}
