package io.github.blockneko11.config.unified.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.ToNumberPolicy;
import io.github.blockneko11.config.unified.exception.SerializationException;
import io.github.blockneko11.config.unified.api.IConfigSerializer;

import java.util.LinkedHashMap;
import java.util.Map;

public class GsonConfigSerializer implements IConfigSerializer {
    public static final GsonConfigSerializer DEFAULT = new GsonConfigSerializer(new GsonBuilder()
            .serializeNulls()
            .setObjectToNumberStrategy(ToNumberPolicy.LAZILY_PARSED_NUMBER)
            .setPrettyPrinting()
            .create());

    private final Gson gson;

    public GsonConfigSerializer(Gson gson) {
        this.gson = gson;
    }

    @Override
    public Map<String, Object> toMap(String s) throws SerializationException {
        try {
            return  (Map<String, Object>) this.gson.fromJson(s, LinkedHashMap.class);
        } catch (JsonSyntaxException e) {
            throw new SerializationException(e);
        }
    }

    @Override
    public String toString(Map<String, Object> config) throws SerializationException {
        return this.gson.toJson(config);
    }
}
