package io.github.blockneko11.config.unified.core;

import io.github.blockneko11.config.unified.conversion.Convertor;
import io.github.blockneko11.config.unified.conversion.Convertors;
import io.github.blockneko11.config.unified.property.Id;
import io.github.blockneko11.config.unified.property.Ignore;
import io.github.blockneko11.config.unified.property.Nest;
import io.github.blockneko11.config.unified.exception.ConfigException;
import io.github.blockneko11.config.unified.serialization.Serializer;
import io.github.blockneko11.config.unified.util.ConstructorUtils;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ReflectiveConfigHolder<T> extends ConfigHolder implements Supplier<T> {
    private final Class<T> clazz;
    private T instance;

    private ReflectiveConfigHolder(Class<T> clazz, Serializer serializer, Supplier<String> loadingAction, Consumer<String> savingAction) {
        super(serializer, loadingAction, savingAction);
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
    protected void load0(String loaded) {
        this.instance = load0(this.clazz, this.serializer.deserialize(loaded));
    }

    private static <T> T load0(Class<T> clazz, Map<String, Object> config) {
        T instance = ConstructorUtils.newInstance(clazz);

        for (Field f : clazz.getDeclaredFields()) {
            if (f.isAnnotationPresent(Ignore.class)) {
                continue;
            }

            int mod = f.getModifiers();
            if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                continue;
            }

            if (f.isAccessible()) {
                f.setAccessible(true);
            }

            String fName = f.isAnnotationPresent(Id.class) ? f.getAnnotation(Id.class).value() : f.getName();
            Object value = config.get(fName);
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

                Convertor<?> convertor = Convertors.get(fType);
                if (convertor == null) {
                    continue;
                }

                f.set(instance, convertor.deserialize(value));
            } catch (IllegalAccessException | ConfigException e) {
                return null;
            }
        }

        return instance;
    }

    @Override
    protected String save0() {
        return this.serializer.serialize(save0(this.clazz, this.instance));
    }

    private static <T> Map<String, Object> save0(Class<T> clazz, T instance) {
        if (instance == null) {
            return Collections.emptyMap();
        }

        Map<String, Object> config = new LinkedHashMap<>();
        for (Field f : clazz.getDeclaredFields()) {
            if (f.isAnnotationPresent(Ignore.class)) {
                continue;
            }

            int mod = f.getModifiers();
            if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                continue;
            }

            if (f.isAccessible()) {
                f.setAccessible(true);
            }

            String fName = f.isAnnotationPresent(Id.class) ? f.getAnnotation(Id.class).value() : f.getName();

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

                Convertor convertor = Convertors.get(fType);
                if (convertor == null) {
                    continue;
                }

                config.put(fName, convertor.serialize(value));
            } catch (IllegalAccessException | ConfigException e) {
                return Collections.emptyMap();
            }
        }

        return config;
    }

    public static <T> Builder<T> builder(Class<T> clazz) {
        return new Builder<>(clazz);
    }

    public static final class Builder<T> extends ConfigHolder.Builder<ReflectiveConfigHolder<T>> {
        private Builder(Class<T> clazz) {
            super((serializer, loadingAction, savingAction) ->
                    new ReflectiveConfigHolder<>(clazz, serializer, loadingAction, savingAction));
        }
    }
}
