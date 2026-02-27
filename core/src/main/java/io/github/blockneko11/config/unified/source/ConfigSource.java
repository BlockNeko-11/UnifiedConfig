package io.github.blockneko11.config.unified.source;

import io.github.blockneko11.config.unified.exception.ConfigException;

public interface ConfigSource {
    String load() throws ConfigException;

    void save(String config) throws ConfigException;
}
