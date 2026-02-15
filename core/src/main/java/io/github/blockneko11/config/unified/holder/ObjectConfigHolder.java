package io.github.blockneko11.config.unified.holder;

import io.github.blockneko11.config.unified.conversion.Convertor;
import io.github.blockneko11.config.unified.conversion.Convertors;
import io.github.blockneko11.config.unified.property.Id;
import io.github.blockneko11.config.unified.property.Ignore;
import io.github.blockneko11.config.unified.property.Nest;
import io.github.blockneko11.config.unified.exception.ConfigException;
import io.github.blockneko11.config.unified.serialization.Serializer;
import io.github.blockneko11.config.unified.util.reflect.ConstructorUtils;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class ObjectConfigHolder<T> implements ConfigHolder, Supplier<T> {
    private final Serializer serializer;
    private final Class<T> clazz;
    private T instance;

    public ObjectConfigHolder(Serializer serializer, Class<T> clazz) {
        this.serializer = serializer;
        this.clazz = clazz;
    }

    @Override
    public Serializer getSerializer() {
        return this.serializer;
    }

    @Nullable
    @Override
    public T get() {
        return this.instance;
    }

    @ApiStatus.Experimental
    public void set(T instance) {
        this.instance = instance;
    }

    public boolean isPresent() {
        return this.instance != null;
    }

    @Override
    public void deserialize(String s) throws ConfigException {
        this.deserialize(this.serializer.deserialize(s));
    }

    private void deserialize(Map<String, Object> config) throws ConfigException {
        this.instance = ConstructorUtils.newInstance(this.clazz);

        for (Field f : this.clazz.getDeclaredFields()) {
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
                    f.setInt(this.instance, ((Number) value).intValue());
                    continue;
                } else if (fType == long.class) {
                    f.setLong(this.instance, ((Number) value).longValue());
                    continue;
                } else if (fType == float.class) {
                    f.setFloat(this.instance, ((Number) value).floatValue());
                    continue;
                } else if (fType == double.class) {
                    f.setDouble(this.instance, ((Number) value).doubleValue());
                    continue;
                } else if (fType == boolean.class) {
                    f.setBoolean(this.instance, (boolean) value);
                    continue;
                } else if (fType == String.class || fType == List.class || fType == Map.class) {
                    f.set(this.instance, value);
                    continue;
                } else if (fType.isEnum()) {
                    f.set(this.instance, Enum.valueOf((Class<? extends Enum>) fType, (String) value));
                    continue;
                } else if (fType.isArray()) {
                    throw new IllegalArgumentException("use List instead of array");
                } else if (f.isAnnotationPresent(Nest.class)) {
                    ObjectConfigHolder<?> subBinder = new ObjectConfigHolder<>(this.serializer, fType);
                    subBinder.deserialize((Map<String, Object>) config.get(fName));
                    if (subBinder.isPresent()) {
                        f.set(this.instance, subBinder.get());
                        continue;
                    }
                }

                Convertor<?> convertor = Convertors.get(fType);
                if (convertor != null) {
                    f.set(this.instance, convertor.deserialize(value));
                }
            } catch (IllegalAccessException e) {
                throw new ConfigException(e);
            }
        }
    }

    @Override
    public String serialize() throws ConfigException {
        if (this.instance == null) {
            return "";
        }

        Map<String, Object> config = new LinkedHashMap<>();
        for (Field f : this.clazz.getDeclaredFields()) {
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
                Object value = f.get(this.instance);

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
                    ObjectConfigHolder subBinder = new ObjectConfigHolder<>(this.serializer, fType);
                    subBinder.set(value);
                    config.put(fName, subBinder.serialize());
                    continue;
                }

                Convertor convertor = Convertors.get(fType);
                if (convertor != null) {
                    config.put(fName, convertor.serialize(value));
                }
            } catch (IllegalAccessException e) {
                throw new ConfigException(e);
            }
        }

        return this.serializer.serialize(config);
    }
}
