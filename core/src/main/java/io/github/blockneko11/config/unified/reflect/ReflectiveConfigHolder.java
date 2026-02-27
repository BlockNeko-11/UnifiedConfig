package io.github.blockneko11.config.unified.reflect;

import io.github.blockneko11.config.unified.core.ConfigHolder;
import io.github.blockneko11.config.unified.conversion.ConfigConvertor;
import io.github.blockneko11.config.unified.conversion.ConfigConvertors;
import io.github.blockneko11.config.unified.conversion.Convert;
import io.github.blockneko11.config.unified.property.Nest;
import io.github.blockneko11.config.unified.exception.ConfigException;
import io.github.blockneko11.config.unified.serialization.ConfigSerializer;
import io.github.blockneko11.config.unified.source.ConfigSource;
import io.github.blockneko11.config.unified.util.ConstructorUtil;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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

        for (Field f : clazz.getDeclaredFields()) {
            int mod = f.getModifiers();
            if (Modifier.isStatic(mod) || Modifier.isFinal(mod) || Modifier.isTransient(mod)) {
                continue;
            }

            if (f.isAccessible()) {
                f.setAccessible(true);
            }

            Object value = config.get(f.getName());
            if (value == null) {
                continue;
            }

            Class<?> fType = f.getType();

            try {
                if (fType == int.class) {
                    f.setInt(instance, ((Number) value).intValue());
                    continue;
                } else if (fType == long.class) {
                    f.setLong(instance, ((Number) value).longValue());
                    continue;
                } else if (fType == float.class) {
                    f.setFloat(instance, ((Number) value).floatValue());
                    continue;
                } else if (fType == double.class) {
                    f.setDouble(instance, ((Number) value).doubleValue());
                    continue;
                } else if (fType == boolean.class) {
                    f.setBoolean(instance, (boolean) value);
                    continue;
                } else if (fType == String.class || fType == List.class || fType == Map.class) {
                    f.set(instance, value);
                    continue;
                } else if (fType.isEnum()) {
                    f.set(instance, Enum.valueOf((Class<? extends Enum>) fType, (String) value));
                    continue;
                } else if (fType.isArray()) {
                    throw new IllegalArgumentException("use List instead of array");
                } else if (f.isAnnotationPresent(Nest.class)) {
                    if (!(value instanceof Map)) {
                        continue;
                    }

                    Object o = load0(fType, (Map<String, Object>) value);
                    if (o == null) {
                        continue;
                    }

                    f.set(instance, o);
                    continue;
                }

                if (f.isAnnotationPresent(Convert.class) && ConfigConvertors.has(fType)) {
                    ConfigConvertor<?> convertor = ConfigConvertors.get(fType);
                    f.set(instance, convertor.toTarget(value));
                }
            } catch (IllegalAccessException e) {
                throw new ConfigException(e);
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
        for (Field f : clazz.getDeclaredFields()) {
            int mod = f.getModifiers();
            if (Modifier.isStatic(mod) || Modifier.isFinal(mod)|| Modifier.isTransient(mod)) {
                continue;
            }

            if (f.isAccessible()) {
                f.setAccessible(true);
            }

            String fName = f.getName();

            try {
                Class<?> fType = f.getType();
                Object value = f.get(instance);

                if (value == null) {
                    continue;
                }

                if (fType == int.class ||
                        fType == long.class ||
                        fType == float.class ||
                        fType == double.class ||
                        fType == boolean.class ||
                        fType == String.class ||
                        fType == List.class ||
                        fType == Map.class) {
                    config.put(fName, value);
                    continue;
                } else if (fType.isEnum()) {
                    config.put(fName, ((Enum<?>) value).name());
                    continue;
                } else if (fType.isArray()) {
                    throw new IllegalArgumentException("use List instead of array");
                } else if (f.isAnnotationPresent(Nest.class)) {
                    Map<String, Object> map = save0((Class) fType, value);

                    if (map.isEmpty()) {
                        continue;
                    }

                    config.put(fName, map);
                }

                if (f.isAnnotationPresent(Convert.class) && ConfigConvertors.has(fType)) {
                    ConfigConvertor convertor = ConfigConvertors.get(fType);
                    config.put(fName, convertor.toSerialized(value));
                }
            } catch (IllegalAccessException e) {
                throw new ConfigException(e);
            }
        }

        return config;
    }

    public static <T> Builder<T> builder(Class<T> clazz) {
        return new Builder<>(clazz);
    }

    public static final class Builder<T> extends ConfigHolder.Builder<ReflectiveConfigHolder<T>> {
        private Builder(Class<T> clazz) {
            super((serializer, source) ->
                    new ReflectiveConfigHolder<>(clazz, serializer, source));
        }
    }
}
