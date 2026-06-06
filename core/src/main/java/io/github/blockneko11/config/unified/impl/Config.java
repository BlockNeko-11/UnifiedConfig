package io.github.blockneko11.config.unified.impl;

import io.github.blockneko11.config.unified.api.IConfig;
import io.github.blockneko11.config.unified.api.IConfigSerializer;
import io.github.blockneko11.config.unified.api.source.IConfigSource;
import io.github.blockneko11.config.unified.exception.ConfigException;

import java.util.LinkedHashMap;
import java.util.Map;

public class Config implements IConfig {
    private Map<String, Object> config = new LinkedHashMap<>();

    @Override
    public boolean isPresent(String key) {
        return this.config.containsKey(key);
    }

    @Override
    public void addBool(String key, boolean bool) {
        this.config.put(key, bool);
    }

    @Override
    public void addInt(String key, int i) {
        this.config.put(key, i);
    }

    @Override
    public void addLong(String key, long l) {
        this.config.put(key, l);
    }

    @Override
    public void addFloat(String key, float f) {
        this.config.put(key, f);
    }

    @Override
    public void addDouble(String key, double d) {
        this.config.put(key, d);
    }

    @Override
    public void addString(String key, String s) {
        this.config.put(key, s);
    }

    @Override
    public void add(String key, Object o) {
        this.config.put(key, o);
    }

    @Override
    public boolean getBool(String key) {
        return (boolean) this.config.get(key);
    }

    @Override
    public int getInt(String key) {
        return ((Number) this.config.get(key)).intValue();
    }

    @Override
    public long getLong(String key) {
        return ((Number) this.config.get(key)).longValue();
    }

    @Override
    public float getFloat(String key) {
        return ((Number) this.config.get(key)).floatValue();
    }

    @Override
    public double getDouble(String key) {
        return ((Number) this.config.get(key)).doubleValue();
    }

    @Override
    public String getString(String key) {
        return (String) this.config.get(key);
    }

    @Override
    public <T> T get(String key) {
        return (T) this.config.get(key);
    }

    @Override
    public void set(String key, Object o) {
        this.config.put(key, o);
    }

    @Override
    public void reset() {
        this.config.clear();
    }

    @Override
    public void clear() {
        this.config.clear();
    }

    @Override
    public void load(IConfigSource source, IConfigSerializer serializer) throws ConfigException {
        String c = source.load();
        if (c == null || c.isEmpty()) {
            return;
        }

        this.config = serializer.toMap(c);
    }

    @Override
    public void save(IConfigSource source, IConfigSerializer serializer) throws ConfigException {
        source.save(serializer.toString(this.config));
    }

    @Override
    public Map<String, Object> asMap() {
        return new LinkedHashMap<>(this.config);
    }

    protected void setConfig(Map<String, Object> config) {
        this.config = config;
    }
}
