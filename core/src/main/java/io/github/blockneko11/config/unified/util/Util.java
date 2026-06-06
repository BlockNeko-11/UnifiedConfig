package io.github.blockneko11.config.unified.util;

import io.github.blockneko11.config.unified.exception.ReflectionException;

import java.lang.reflect.Constructor;
import java.util.function.Consumer;

public final class Util {
    public static <T> T withInitialize(T object, Consumer<? super T> initializer) {
        initializer.accept(object);
        return object;
    }

    public static <T> T construct(Class<T> clazz) throws ReflectionException {
        try {
            Constructor<T> ctor = clazz.getDeclaredConstructor();
            if (!ctor.isAccessible()) {
                ctor.setAccessible(true);
            }

            return ctor.newInstance();
        } catch (Exception e) {
            throw new ReflectionException(e);
        }
    }

    private Util() {
    }
}
