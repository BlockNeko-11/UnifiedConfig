package io.github.blockneko11.config.unified.impl.source;

import io.github.blockneko11.config.unified.api.source.IConfigSource;
import io.github.blockneko11.config.unified.exception.ConfigException;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class SimpleConfigSource implements IConfigSource {
    private final Supplier<String> loadingAction;
    private final Consumer<String> savingAction;

    public SimpleConfigSource(Supplier<String> loadingAction, Consumer<String> savingAction) {
        this.loadingAction = loadingAction;
        this.savingAction = savingAction;
    }

    @Override
    public String load() throws ConfigException {
        return this.loadingAction.get();
    }

    @Override
    public void save(String config) throws ConfigException {
        this.savingAction.accept(config);
    }
}
