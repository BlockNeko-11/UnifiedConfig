package io.github.blockneko11.config.unified.util;

import java.util.function.Consumer;

public final class Util {
    public static <T> T withInitialize(T object, Consumer<? super T> initializer) {
        initializer.accept(object);
        return object;
    }

    private Util() {
    }
}
