package io.github.blockneko11.config.unified.conversion;

import io.github.blockneko11.config.unified.exception.ConfigException;
import io.github.blockneko11.config.unified.util.ConstructorUtil;
import io.github.blockneko11.config.unified.validation.Validations;

import java.lang.reflect.Field;

public final class Conversions {
    public static <T> T deserialize(Field field, Object value) throws ConfigException {
        Conversion anno = field.getAnnotation(Conversion.class);
        if (anno == null) {
            return null;
        }

        Class<?> valueType = value.getClass();
        ConfigConvertor<Object, Object> convertor = (ConfigConvertor<Object, Object>) ConstructorUtil.newInstance(anno.value());
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

        ConfigConvertor<Object, Object> convertor = (ConfigConvertor<Object, Object>) ConstructorUtil.newInstance(anno.value());
        return convertor.serialize(value);
    }

    private Conversions() {
    }
}
