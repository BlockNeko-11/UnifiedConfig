package io.github.blockneko11.config.unified.holder;

import io.github.blockneko11.config.unified.serialization.Serializer;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MapConfigHolder implements ConfigHolder {
    protected final Map<String, Object> config;
    private final Serializer serializer;

    public MapConfigHolder(Serializer serializer) {
        this.config = new LinkedHashMap<>();
        this.serializer = serializer;
    }

    @Override
    public Serializer getSerializer() {
        return this.serializer;
    }

    public boolean isEmpty() {
        return this.config.isEmpty();
    }

    public boolean hasKey(String key) {
        return this.config.containsKey(key);
    }

    public void set(String key, Object value) {
        if (value == null) {
            this.config.remove(key);
            return;
        }

        this.config.put(key, value);
    }

    public <T> T get(String key) {
        return (T) this.config.get(key);
    }

    public <T> T get(String key, T defaultValue) {
        return this.hasKey(key) ? (T) this.config.get(key) : defaultValue;
    }

    public int getInt(String key) {
        return this.get(key);
    }

    public int getInt(String key, int defaultValue) {
        return this.get(key, defaultValue);
    }

    public long getLong(String key) {
        return this.get(key);
    }

    public long getLong(String key, long defaultValue) {
        return this.get(key, defaultValue);
    }

    public float getFloat(String key) {
        return this.get(key);
    }

    public float getFloat(String key, float defaultValue) {
        return this.get(key, defaultValue);
    }

    public double getDouble(String key) {
        return this.get(key);
    }

    public double getDouble(String key, double defaultValue) {
        return this.get(key, defaultValue);
    }

    public boolean getBoolean(String key) {
        return this.get(key);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return this.get(key, defaultValue);
    }

    public <T> T getPath(String path) {
        return this.getPath(path.split("\\."));
    }

    private <T> T getPath(String... keys) {
        Object value = this.config;

        for (String key : keys) {
            if (value instanceof Map<?, ?>) {
                value = ((Map<?, ?>) value).get(key);
            } else if (value instanceof List<?>) {
                value = ((List<?>) value).get(Integer.parseInt(key));
            } else {
                throw new IllegalArgumentException("invalid path");
            }
        }

        return (T) value;
    }

    public void merge(MapConfigHolder holder) {
        this.merge(holder.config);
    }

    public void merge(Map<String, Object> another) {
        this.merge(another, true);
    }

    public void merge(MapConfigHolder another, boolean replace) {
        this.merge(another.config, true);
    }

    public void merge(Map<String, Object> another, boolean replace) {
        for (Map.Entry<String, Object> entry : another.entrySet()) {
            if (replace || !this.hasKey(entry.getKey())) {
                this.set(entry.getKey(), entry.getValue());
            }
        }
    }

    @Override
    public void deserialize(String s) {
        this.merge(this.serializer.deserialize(s));
    }

    @Override
    public String serialize() {
        return this.serializer.serialize(this.config);
    }

    public Map<String, Object> unwrap() {
        return this.config;
    }
}
