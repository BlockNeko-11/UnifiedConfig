package io.github.blockneko11.config.unified.core;

import io.github.blockneko11.config.unified.serialization.Serializer;

import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class ConfigHolder {
    protected final Serializer serializer;
    private final Supplier<String> loadingAction;
    private final Consumer<String> savingAction;

    protected ConfigHolder(Serializer serializer, Supplier<String> loadingAction, Consumer<String> savingAction) {
        this.serializer = serializer;
        this.loadingAction = loadingAction;
        this.savingAction = savingAction;
    }

    public final void load() {
        this.load0(this.loadingAction.get());
    }

    protected abstract void load0(String loaded);

    public final void save() {
        this.savingAction.accept(this.save0());
    }

    protected abstract String save0();

    public static abstract class Builder<T extends ConfigHolder> {
        private final ConfigCreator<T> creator;
        private Serializer serializer;
        private Supplier<String> loadingAction = () -> "";
        private Consumer<String> savingAction = s -> {};

        protected Builder(ConfigCreator<T> creator) {
            this.creator = creator;
        }

        public final Builder<T> serializer(Serializer serializer) {
            this.serializer = serializer;
            return this;
        }

        public final Builder<T> loadingAction(Supplier<String> loadingAction) {
            this.loadingAction = loadingAction;
            return this;
        }

        public final Builder<T> savingAction(Consumer<String> savingAction) {
            this.savingAction = savingAction;
            return this;
        }

        public final T build() {
            if (this.serializer == null) {
                throw new IllegalStateException("serializer is not set");
            }

            return this.creator.apply(this.serializer, this.loadingAction, this.savingAction);
        }
    }

    @FunctionalInterface
    public interface ConfigCreator<T extends ConfigHolder> {
        T apply(Serializer serializer, Supplier<String> loadingAction, Consumer<String> savingAction);
    }
}
