package io.github.blockneko11.config.unified.util;

public final class ConstructorUtils {
    public static <T> T newInstance(Class<T> clazz) {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new IllegalArgumentException("cannot create instance of " + clazz.getName(), e);
        }
    }
}
