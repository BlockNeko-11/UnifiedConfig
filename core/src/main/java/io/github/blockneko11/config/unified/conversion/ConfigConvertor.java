package io.github.blockneko11.config.unified.conversion;

import io.github.blockneko11.config.unified.exception.ConversionException;

public interface ConfigConvertor<T, F> {
    Class<F> getOriginalType();

    T deserialize(F config) throws ConversionException;

    F serialize(T field) throws ConversionException;
}
