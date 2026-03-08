package io.github.blockneko11.config.unified.conversion;

import io.github.blockneko11.config.unified.api.convert.ConfigConvertor;
import io.github.blockneko11.config.unified.exception.ConversionException;

import java.util.UUID;

public class UUIDConvertor implements ConfigConvertor<UUID> {
    @Override
    public UUID deserialize(Object config) throws ConversionException {
        if (!(config instanceof String)) {
            throw new ConversionException("config value is not a string");
        }

        return UUID.fromString((String) config);
    }

    @Override
    public Object serialize(UUID field) throws ConversionException {
        return field.toString();
    }
}
