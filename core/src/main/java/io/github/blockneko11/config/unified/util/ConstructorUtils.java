package io.github.blockneko11.config.unified.util;

import io.github.blockneko11.config.unified.exception.ConfigException;

public final class ConstructorUtils {
    public static <T> T newInstance(Class<T> clazz) throws ConfigException {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new ConfigException("Cannot create instance of " + clazz.getName(), e);
        }
    }
}
