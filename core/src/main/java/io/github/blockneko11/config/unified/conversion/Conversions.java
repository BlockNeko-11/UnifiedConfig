package io.github.blockneko11.config.unified.conversion;

import io.github.blockneko11.config.unified.exception.ConfigException;
import io.github.blockneko11.config.unified.util.ConstructorUtil;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public final class Conversions {
    private static final Map<Class<? extends ConfigConvertor<?, ?>>, ConfigConvertor<?, ?>> CONVERTORS = new HashMap<>();

    public static <T> T deserialize(Field field, Object value) throws ConfigException {
        Conversion anno = field.getAnnotation(Conversion.class);
        if (anno == null) {
            return null;
        }

        Class<?> valueType = value.getClass();
        Class<? extends ConfigConvertor<?, ?>> convertorClass = anno.value();
        if (!CONVERTORS.containsKey(convertorClass)) {
            CONVERTORS.put(convertorClass, ConstructorUtil.newInstance(convertorClass));
        }

        ConfigConvertor<Object, Object> convertor = (ConfigConvertor<Object, Object>) CONVERTORS.get(convertorClass);
        if (!convertor.getOriginalType().isAssignableFrom(valueType)) {
            throw new IllegalArgumentException("config type " + valueType + " is not assignable from " + convertor.getOriginalType());
        }

        return (T) convertor.deserialize(value);
    }

    public static Object serialize(Field field, Object value) throws ConfigException {
        Conversion anno = field.getAnnotation(Conversion.class);
        if (anno == null) {
            return null;
        }

        Class<? extends ConfigConvertor<?, ?>> convertorClass = anno.value();
        if (!CONVERTORS.containsKey(convertorClass)) {
            CONVERTORS.put(convertorClass, ConstructorUtil.newInstance(convertorClass));
        }

        ConfigConvertor<Object, Object> convertor = (ConfigConvertor<Object, Object>) CONVERTORS.get(convertorClass);
        return convertor.serialize(value);
    }

    private Conversions() {
    }
}
