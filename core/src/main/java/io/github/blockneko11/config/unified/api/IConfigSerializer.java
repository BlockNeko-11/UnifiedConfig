package io.github.blockneko11.config.unified.api;

import io.github.blockneko11.config.unified.exception.SerializationException;

import java.util.Map;

public interface IConfigSerializer {
    Map<String, Object> toMap(String config) throws SerializationException;

    String toString(Map<String, Object> config) throws SerializationException;
}
