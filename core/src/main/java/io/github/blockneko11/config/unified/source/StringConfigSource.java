package io.github.blockneko11.config.unified.source;

import io.github.blockneko11.config.unified.exception.ConfigException;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class StringConfigSource implements ConfigSource {
    private final Supplier<String> loader;
    private final Consumer<String> saver;

    public StringConfigSource(Supplier<String> loader, Consumer<String> saver) {
        this.loader = loader;
        this.saver = saver;
    }

    @Override
    public String load() throws ConfigException {
        return this.loader.get();
    }

    @Override
    public void save(String config) throws ConfigException {
        this.saver.accept(config);
    }
}
