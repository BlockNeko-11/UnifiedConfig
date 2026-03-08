package io.github.blockneko11.config.unified.api.convert;

import io.github.blockneko11.config.unified.exception.ConversionException;

public interface ConfigConvertor<T> {
    T deserialize(Object config) throws ConversionException;

    Object serialize(T field) throws ConversionException;
}
