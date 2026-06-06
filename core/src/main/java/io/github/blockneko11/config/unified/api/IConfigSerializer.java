package io.github.blockneko11.config.unified.api;

import io.github.blockneko11.config.unified.exception.SerializationException;

import java.util.Map;

public interface IConfigSerializer {

    // for IConfig

    Map<String, Object> toMap(String config) throws SerializationException;

    // for IConfigHolder

    <T> T toObject(Class<T> configClass, String config) throws SerializationException;

    // common serialization

    String toString(Object object) throws SerializationException;
}
