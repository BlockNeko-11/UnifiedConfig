package io.github.blockneko11.config.unified.serialization;

import io.github.blockneko11.config.unified.exception.ConfigSerializationException;

import java.util.Map;

public interface ConfigSerializer {
    Map<String, Object> deserialize(String config) throws ConfigSerializationException;

    String serialize(Map<String, Object> config) throws ConfigSerializationException;
}
