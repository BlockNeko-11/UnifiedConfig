package io.github.blockneko11.config.unified.core;

import io.github.blockneko11.config.unified.exception.ConfigException;
import io.github.blockneko11.config.unified.serialization.ConfigSerializer;
import io.github.blockneko11.config.unified.source.ConfigSource;

public abstract class ConfigHolder {
    protected final ConfigSerializer serializer;
    private final ConfigSource source;

    protected ConfigHolder(ConfigSerializer serializer, ConfigSource source) {
        this.serializer = serializer;
        this.source = source;
    }

    public final void load() throws ConfigException {
        this.load0(this.source.load());
    }

    protected abstract void load0(String loaded) throws ConfigException;

    public final void save() throws ConfigException {
        this.source.save(this.save0());
    }

    protected abstract String save0() throws ConfigException;

    public static abstract class Builder<T extends ConfigHolder> {
        private final ConfigCreator<T> creator;
        private ConfigSerializer serializer;
        private ConfigSource source;

        protected Builder(ConfigCreator<T> creator) {
            this.creator = creator;
        }

        public final Builder<T> serializer(ConfigSerializer serializer) {
            this.serializer = serializer;
            return this;
        }

        public final Builder<T> source(ConfigSource source) {
            this.source = source;
            return this;
        }

        public final T build() {
            if (this.serializer == null) {
                throw new IllegalStateException("serializer is not set");
            }

            return this.creator.apply(this.serializer, this.source);
        }
    }

    @FunctionalInterface
    public interface ConfigCreator<T extends ConfigHolder> {
        T apply(ConfigSerializer serializer, ConfigSource source);
    }
}
