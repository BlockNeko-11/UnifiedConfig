package io.github.blockneko11.config.unified.api;

import io.github.blockneko11.config.unified.api.source.IConfigSource;
import io.github.blockneko11.config.unified.exception.ConfigException;

import java.util.Map;

public interface IConfig {
    boolean isPresent(String key);

    void addBool(String key, boolean bool);

    void addInt(String key, int i);

    void addLong(String key, long l);

    void addFloat(String key, float f);

    void addDouble(String key, double d);

    void addString(String key, String s);

    void add(String key, Object o);

    boolean getBool(String key);

    int getInt(String key);

    default int getInt(String key, int defaultValue) {
        return isPresent(key) ? getInt(key) : defaultValue;
    }

    long getLong(String key);

    default long getLong(String key, long defaultValue) {
        return isPresent(key) ? getLong(key) : defaultValue;
    }

    float getFloat(String key);

    default float getFloat(String key, float defaultValue) {
        return isPresent(key) ? getFloat(key) : defaultValue;
    }

    double getDouble(String key);

    default double getDouble(String key, double defaultValue) {
        return isPresent(key) ? getDouble(key) : defaultValue;
    }

    String getString(String key);

    default String getString(String key, String defaultValue) {
        return isPresent(key) ? getString(key) : defaultValue;
    }

    <T> T get(String key);

    default <T> T get(String key, T defaultValue) {
        return isPresent(key) ? get(key) : defaultValue;
    }

    void set(String key, Object o);

    default void setIfAbsent(String key, Object o) {
        if (!isPresent(key)) {
            set(key, o);
        }
    }

    void reset();

    void clear();

    void load(IConfigSource source, IConfigSerializer serializer) throws ConfigException;

    void save(IConfigSource source, IConfigSerializer serializer) throws ConfigException;

    Map<String, Object> asMap();
}
