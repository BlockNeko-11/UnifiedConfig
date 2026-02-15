package io.github.blockneko11.config.unified.conversion;

import io.github.blockneko11.config.unified.exception.ConversionException;

public interface Convertor<T> {
     Class<T> getConvertedType();

    T deserialize(Object config) throws ConversionException; // @NotNull S config

    Object serialize(T field) throws ConversionException;
}
