package io.github.blockneko11.config.unified.serialization;

import java.util.Map;

public interface Serializer {
    Map<String, Object> deserialize(String config);

    String serialize(Map<String, Object> config);
}
