package io.github.blockneko11.config.unified.conversion;

import java.util.HashMap;
import java.util.Map;

public final class Convertors {
    private static final Map<Class<?>, Convertor<?>> CONVERTORS = new HashMap<>();

    public static void register(Convertor<?> convertor) {
        CONVERTORS.put(convertor.getConvertedType(), convertor);
    }

    public static <T> Convertor<T> get(Class<T> clazz) {
        return (Convertor<T>) CONVERTORS.get(clazz);
    }
}
