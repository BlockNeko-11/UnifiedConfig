package io.github.blockneko11.config.unified.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import io.github.blockneko11.config.unified.serialization.Serializer;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class GsonSerializer implements Serializer {
    public static final Gson DEFAULT_GSON = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
    public static final GsonSerializer DEFAULT = new GsonSerializer(DEFAULT_GSON);

    private final Gson gson;

    public GsonSerializer(Gson gson) {
        this.gson = gson;
    }

    @Override
    public Map<String, Object> deserialize(String config) {
        try {
            return (Map<String, Object>) this.gson.fromJson(config, LinkedHashMap.class);
        } catch (JsonSyntaxException e) {
            return Collections.emptyMap();
        }
    }

    @Override
    public String serialize(Map<String, Object> config) {
        return this.gson.toJson(config);
    }
}
