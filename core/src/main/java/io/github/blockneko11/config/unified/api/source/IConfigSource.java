package io.github.blockneko11.config.unified.api.source;

import io.github.blockneko11.config.unified.exception.ConfigException;

public interface IConfigSource {
    String load() throws ConfigException;

    void save(String config) throws ConfigException;
}
