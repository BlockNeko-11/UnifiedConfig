package io.github.blockneko11.config.unified.conversion;

import io.github.blockneko11.config.unified.exception.ConfigConversionException;

import java.util.UUID;

public class UUIDConvertor implements ConfigConvertor<UUID> {
    @Override
    public Class<UUID> getTargetType() {
        return UUID.class;
    }

    @Override
    public UUID toTarget(Object config) throws ConfigConversionException {
        if (!(config instanceof String)) {
            throw new ConfigConversionException("UUID must be a string");
        }

        return UUID.fromString((String) config);
    }

    @Override
    public Object toSerialized(UUID field) throws ConfigConversionException {
        return field.toString();
    }
}
