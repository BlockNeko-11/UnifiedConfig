package io.github.blockneko11.config.unified.conversion;

import java.util.HashMap;
import java.util.Map;

public final class ConfigConvertors {
    private static final Map<Class<?>, ConfigConvertor<?>> CONVERTORS = new HashMap<>();

    public static void register(ConfigConvertor<?> convertor) {
        CONVERTORS.put(convertor.getTargetType(), convertor);
    }

    public static <T> ConfigConvertor<T> get(Class<T> clazz) {
        return (ConfigConvertor<T>) CONVERTORS.get(clazz);
    }

    public static boolean has(Class<?> clazz) {
        return CONVERTORS.containsKey(clazz);
    }

    static {
        register(new UUIDConvertor());
    }
}
