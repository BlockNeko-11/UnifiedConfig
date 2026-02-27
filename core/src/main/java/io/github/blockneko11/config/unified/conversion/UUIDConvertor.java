package io.github.blockneko11.config.unified.conversion;

import io.github.blockneko11.config.unified.exception.ConversionException;

import java.util.UUID;

public class UUIDConvertor implements ConfigConvertor<UUID, String> {
    @Override
    public Class<String> getOriginalType() {
        return String.class;
    }

    @Override
    public UUID deserialize(String config) throws ConversionException {
        return UUID.fromString(config);
    }

    @Override
    public String serialize(UUID field) throws ConversionException {
        return field.toString();
    }
}
