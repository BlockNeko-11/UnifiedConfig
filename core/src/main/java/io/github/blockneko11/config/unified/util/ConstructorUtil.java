package io.github.blockneko11.config.unified.util;

import io.github.blockneko11.config.unified.exception.ReflectionException;

import java.lang.reflect.Constructor;

public final class ConstructorUtil {
    public static <T> T newInstance(Class<T> clazz) throws ReflectionException {
        try {
            Constructor<T> ctor = clazz.getDeclaredConstructor();
            if (!ctor.isAccessible()) {
                ctor.setAccessible(true);
            }

            return ctor.newInstance();
        } catch (ReflectiveOperationException e) {
            throw new ReflectionException("Cannot create instance of " + clazz.getName(), e);
        }
    }
}
