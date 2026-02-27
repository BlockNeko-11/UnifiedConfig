package io.github.blockneko11.config.unified.reflect;

import io.github.blockneko11.config.unified.conversion.ConfigConvertor;
import io.github.blockneko11.config.unified.conversion.Conversion;
import io.github.blockneko11.config.unified.conversion.Conversions;
import io.github.blockneko11.config.unified.core.ConfigHolder;
import io.github.blockneko11.config.unified.core.ConfigHolderBuilder;
import io.github.blockneko11.config.unified.exception.ReflectionException;
import io.github.blockneko11.config.unified.property.Nest;
import io.github.blockneko11.config.unified.exception.ConfigException;
import io.github.blockneko11.config.unified.serialization.ConfigSerializer;
import io.github.blockneko11.config.unified.source.ConfigSource;
import io.github.blockneko11.config.unified.util.ConstructorUtil;
import io.github.blockneko11.config.unified.validation.*;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.function.Supplier;

public class ReflectiveConfigHolder<T> extends ConfigHolder implements Supplier<T> {
    private final Class<T> clazz;
    private T instance;

    private ReflectiveConfigHolder(Class<T> clazz, ConfigSerializer serializer, ConfigSource source) {
        super(serializer, source);
        this.clazz = clazz;
    }

    @Nullable
    @Override
    public T get() {
        return this.instance;
    }

    @ApiStatus.Internal
    public void set(T instance) {
        this.instance = instance;
    }

    public boolean isPresent() {
        return this.instance != null;
    }

    @Override
    protected void load0(String loaded) throws ConfigException {
        this.instance = load0(this.clazz, this.serializer.deserialize(loaded));
    }

    private static <T> T load0(Class<T> clazz, Map<String, Object> config) throws ConfigException {
        T instance = ConstructorUtil.newInstance(clazz);

        for (Field field : clazz.getDeclaredFields()) {
            int mod = field.getModifiers();
            if (Modifier.isStatic(mod) || Modifier.isFinal(mod) || Modifier.isTransient(mod)) {
                continue;
            }

            if (!field.isAccessible()) {
                field.setAccessible(true);
            }

            try {
                String fieldName = field.getName();
                Object value = config.get(fieldName);
                if (value == null) {
                    Validations.validateNull(field);
                    field.set(instance, null);
                    continue;
                }

                Class<?> fieldType = field.getType();

                if (fieldType == int.class) {
                    int i = ((Number) value).intValue();
                    Validations.validateInt(field, i);
                    field.setInt(instance, i);
                    continue;
                } if (fieldType == long.class) {
                    long l = ((Number) value).longValue();
                    Validations.validateLong(field, l);
                    continue;
                } if (fieldType == float.class) {
                    float f = ((Number) value).floatValue();
                    Validations.validateFloat(field, f);
                    continue;
                } if (fieldType == double.class) {
                    double d = ((Number) value).doubleValue();
                    Validations.validateDouble(field, d);
                    continue;
                } if (fieldType == boolean.class) {
                    field.setBoolean(instance, (boolean) value);
                    continue;
                } if (fieldType == char.class || fieldType == byte.class || fieldType == short.class) {
                    throw new ConfigException("char, byte and short are not supported");
                } if (fieldType == String.class) {
                    String s = (String) value;
                    Validations.validateString(field, s);
                    field.set(instance, s);
                    continue;
                } if (fieldType == List.class || fieldType == Map.class) {
                    field.set(instance, value);
                    continue;
                } if (fieldType.isEnum()) {
                    field.set(instance, Enum.valueOf((Class<? extends Enum>) fieldType, (String) value));
                    continue;
                } if (fieldType.isArray()) {
                    throw new ConfigException("use List instead of array");
                } if (field.isAnnotationPresent(Nest.class)) {
                    if (!(value instanceof Map)) {
                        throw new ConfigException("config value of key " + fieldName + " is not a map");
                    }

                    field.set(instance, load0(fieldType, (Map<String, Object>) value));
                    continue;
                }

                Object deserialized = Conversions.deserialize(field, value);
                if (deserialized == null) {
                    continue;
                }

                Validations.validateObject(field, deserialized);
                field.set(instance, deserialized);
            } catch (IllegalAccessException e) {
                throw new ReflectionException(e);
            }
        }

        return instance;
    }

    @Override
    protected String save0() throws ConfigException {
        return this.serializer.serialize(save0(this.clazz, this.instance));
    }

    private static <T> Map<String, Object> save0(Class<T> clazz, T instance) throws ConfigException {
        if (instance == null) {
            return Collections.emptyMap();
        }

        Map<String, Object> config = new LinkedHashMap<>();
        for (Field field : clazz.getDeclaredFields()) {
            int mod = field.getModifiers();
            if (Modifier.isStatic(mod) || Modifier.isFinal(mod)|| Modifier.isTransient(mod)) {
                continue;
            }

            if (!field.isAccessible()) {
                field.setAccessible(true);
            }

            try {
                Class<?> fieldType = field.getType();
                String fieldName = field.getName();
                Object value = field.get(instance);

                if (value == null) {
                    Validations.validateNull(field);
                    config.put(fieldName, null);
                    continue;
                } if (fieldType == int.class) {
                    int i = field.getInt(instance);
                    Validations.validateInt(field, i);
                    config.put(fieldName, i);
                    continue;
                } if (fieldType == long.class) {
                    long l = field.getLong(instance);
                    Validations.validateLong(field, l);
                    config.put(fieldName, l);
                    continue;
                } if (fieldType == float.class) {
                    float f = field.getFloat(instance);
                    Validations.validateFloat(field, f);
                    config.put(fieldName, f);
                    continue;
                } if (fieldType == double.class) {
                    double d = field.getDouble(instance);
                    Validations.validateDouble(field, d);
                    config.put(fieldName, d);
                    continue;
                } if (fieldType == char.class || fieldType == byte.class || fieldType == short.class) {
                    throw new ConfigException("char, byte and short are not supported");
                } if (fieldType == String.class) {
                    String s = (String) value;
                    Validations.validateString(field, s);
                    config.put(fieldName, s);
                    continue;
                } if (fieldType == boolean.class || fieldType == List.class || fieldType == Map.class) {
                    config.put(fieldName, value);
                    continue;
                } if (fieldType.isEnum()) {
                    config.put(fieldName, ((Enum<?>) value).name());
                    continue;
                } if (fieldType.isArray()) {
                    throw new ConfigException("use List instead of array");
                } if (field.isAnnotationPresent(Nest.class)) {
                    Map<String, Object> map = save0((Class) fieldType, value);

                    if (map.isEmpty()) {
                        continue;
                    }

                    config.put(fieldName, map);
                    continue;
                }

                Validations.validateObject(field, value);
                config.put(fieldName, Conversions.serialize(field, value));
            } catch (IllegalAccessException e) {
                throw new ReflectionException(e);
            }
        }

        return config;
    }



    public static <T> Builder<T> builder(Class<T> clazz) {
        return new Builder<>(clazz);
    }

    public static final class Builder<T> extends ConfigHolderBuilder<ReflectiveConfigHolder<T>> {
        private Builder(Class<T> clazz) {
            super((serializer, source) ->
                    new ReflectiveConfigHolder<>(clazz, serializer, source));
        }
    }
}
