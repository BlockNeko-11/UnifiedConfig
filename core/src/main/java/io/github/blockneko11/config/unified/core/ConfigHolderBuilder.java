package io.github.blockneko11.config.unified.core;

import io.github.blockneko11.config.unified.serialization.ConfigSerializer;
import io.github.blockneko11.config.unified.source.ConfigSource;

public class ConfigHolderBuilder<T extends ConfigHolder> {
    private final Factory<T> factory;
    private ConfigSerializer serializer;
    private ConfigSource source;

    protected ConfigHolderBuilder(Factory<T> factory) {
        this.factory = factory;
    }

    public static <T extends ConfigHolder> ConfigHolderBuilder<T> of(Factory<T> factory) {
        return new ConfigHolderBuilder<>(factory);
    }

    public final ConfigHolderBuilder<T> serializer(ConfigSerializer serializer) {
        this.serializer = serializer;
        return this;
    }

    public final ConfigHolderBuilder<T> source(ConfigSource source) {
        this.source = source;
        return this;
    }

    public final T build() {
        if (this.serializer == null) {
            throw new IllegalStateException("serializer is not set");
        }

        return this.factory.apply(this.serializer, this.source);
    }

    @FunctionalInterface
    public interface Factory<T extends ConfigHolder> {
        T apply(ConfigSerializer serializer, ConfigSource source);
    }
}
