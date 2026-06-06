package io.github.blockneko11.config.unified.api;

import io.github.blockneko11.config.unified.api.source.IConfigSource;
import io.github.blockneko11.config.unified.exception.ConfigException;

import java.util.function.Supplier;

public interface IConfigHolder<T> extends Supplier<T> {
    Class<T> getConfigClass();

    default boolean isPresent() {
        return get() != null;
    }

    @Override
    T get();

    void set(T config);

    void reset() throws ConfigException;

    default void resetIfAbsent() throws ConfigException {
        if (isPresent()) {
            throw new ConfigException("config is present");
        }

        reset();
    }

    default void clear() {
        set(null);
    }

    void load(IConfigSource source, IConfigSerializer serializer) throws ConfigException;

    void save(IConfigSource source, IConfigSerializer serializer) throws ConfigException;
}
