package io.github.blockneko11.config.unified.holder;

import io.github.blockneko11.config.unified.exception.ConfigException;
import io.github.blockneko11.config.unified.serialization.Serializer;

public interface ConfigHolder {
    Serializer getSerializer();

    void deserialize(String s) throws ConfigException;

    String serialize() throws ConfigException;
}
