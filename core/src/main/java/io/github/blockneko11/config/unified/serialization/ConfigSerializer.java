package io.github.blockneko11.config.unified.serialization;

import io.github.blockneko11.config.unified.exception.SerializationException;

import java.util.Map;

public interface ConfigSerializer {
    Map<String, Object> deserialize(String config) throws SerializationException;

    String serialize(Map<String, Object> config) throws SerializationException;
}
