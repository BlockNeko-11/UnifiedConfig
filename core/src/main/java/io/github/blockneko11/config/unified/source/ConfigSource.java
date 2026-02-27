package io.github.blockneko11.config.unified.source;

public interface ConfigSource {
    String load() throws Exception;

    void save(String config) throws Exception;
}
