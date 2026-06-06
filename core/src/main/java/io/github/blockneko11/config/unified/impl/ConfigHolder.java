package io.github.blockneko11.config.unified.impl;

import io.github.blockneko11.config.unified.api.IConfigHolder;
import io.github.blockneko11.config.unified.api.IConfigSerializer;
import io.github.blockneko11.config.unified.api.source.IConfigSource;
import io.github.blockneko11.config.unified.exception.ConfigException;
import io.github.blockneko11.config.unified.util.Util;

public class ConfigHolder<T> implements IConfigHolder<T> {
    private final Class<T> configClass;

    private T config;

    public ConfigHolder(Class<T> configClass) {
        this.configClass = configClass;
    }

    @Override
    public Class<T> getConfigClass() {
        return this.configClass;
    }

    @Override
    public T get() {
        return this.config;
    }

    @Override
    public void set(T config) {
        this.config = config;
    }

    @Override
    public void reset() throws ConfigException {
        this.config = Util.construct(this.configClass);
    }

    @Override
    public void load(IConfigSource source, IConfigSerializer serializer) throws ConfigException {
        String c = source.load();
        if (c == null || c.isEmpty()) {
            reset();
            return;
        }

        this.config = serializer.toObject(this.configClass, c);
    }

    @Override
    public void save(IConfigSource source, IConfigSerializer serializer) throws ConfigException {
        source.save(serializer.toString(this.config));
    }
}
