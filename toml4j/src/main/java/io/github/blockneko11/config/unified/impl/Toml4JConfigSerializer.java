package io.github.blockneko11.config.unified.impl;

import com.moandjiezana.toml.Toml;
import com.moandjiezana.toml.TomlWriter;
import io.github.blockneko11.config.unified.exception.SerializationException;
import io.github.blockneko11.config.unified.api.IConfigSerializer;

import java.util.Map;

public class Toml4JConfigSerializer implements IConfigSerializer {
    public static final Toml4JConfigSerializer DEFAULT = new Toml4JConfigSerializer();

    public Toml4JConfigSerializer() {
    }

    @Override
    public Map<String, Object> toMap(String config) throws SerializationException {
        return new Toml().read(config).toMap();
    }

    @Override
    public <T> T toObject(Class<T> configClass, String config) throws SerializationException {
        return new Toml().read(config).to(configClass);
    }

    @Override
    public String toString(Object config) throws SerializationException {
        return new TomlWriter().write(config);
    }
}
