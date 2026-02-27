package io.github.blockneko11.config.unified.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import io.github.blockneko11.config.unified.exception.SerializationException;
import io.github.blockneko11.config.unified.serialization.ConfigSerializer;

import java.util.LinkedHashMap;
import java.util.Map;

public class GsonConfigSerializer implements ConfigSerializer {
    public static final Gson DEFAULT_GSON = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
    public static final GsonConfigSerializer DEFAULT = new GsonConfigSerializer(DEFAULT_GSON);

    private final Gson gson;

    public GsonConfigSerializer(Gson gson) {
        this.gson = gson;
    }

    @Override
    public Map<String, Object> deserialize(String config) throws SerializationException {
        try {
            return (Map<String, Object>) this.gson.fromJson(config, LinkedHashMap.class);
        } catch (JsonSyntaxException e) {
            throw new SerializationException(e);
        }
    }

    @Override
    public String serialize(Map<String, Object> config) throws SerializationException {
        return this.gson.toJson(config);
    }
}
