package io.github.blockneko11.config.unified.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import io.github.blockneko11.config.unified.api.IConfigSerializer;
import io.github.blockneko11.config.unified.exception.SerializationException;

import java.util.Map;

public class JacksonConfigSerializer implements IConfigSerializer {
    public static final JacksonConfigSerializer DEFAULT_JSON;
    public static final JacksonConfigSerializer DEFAULT_YAML;

    static {
        DEFAULT_JSON = new JacksonConfigSerializer(new JsonMapper());

        YAMLFactory yamlFactory = new YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER);
        DEFAULT_YAML = new JacksonConfigSerializer(new YAMLMapper(yamlFactory));
    }

    private final ObjectMapper mapper;

    public JacksonConfigSerializer(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Map<String, Object> toMap(String config) throws SerializationException {
        try {
            return this.mapper.readValue(config, new TypeReference<Map<String, Object>>() {});
        } catch (JsonProcessingException e) {
            throw new SerializationException(e);
        }
    }

    @Override
    public <T> T toObject(Class<T> configClass, String config) throws SerializationException {
        try {
            return this.mapper.readValue(config, configClass);
        } catch (JsonProcessingException e) {
            throw new SerializationException(e);
        }
    }

    @Override
    public String toString(Object object) throws SerializationException {
        try {
            return this.mapper.writer().withDefaultPrettyPrinter().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new SerializationException(e);
        }
    }
}
