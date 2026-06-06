package io.github.blockneko11.config.unified.impl;

import io.github.blockneko11.config.unified.exception.SerializationException;
import io.github.blockneko11.config.unified.api.IConfigSerializer;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.nodes.Tag;

import java.util.Map;

public class SnakeYamlConfigSerializer implements IConfigSerializer {
    public static final SnakeYamlConfigSerializer DEFAULT;

    static {
        LoaderOptions lop = new LoaderOptions();
        lop.setProcessComments(false);

        DumperOptions dop = new DumperOptions();
        dop.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        dop.setProcessComments(false);
        dop.setIndent(2);
        dop.setIndicatorIndent(2);
        dop.setIndentWithIndicator(true);
        dop.setPrettyFlow(false);

        Yaml $yaml = new Yaml(lop, dop);
        DEFAULT = new SnakeYamlConfigSerializer($yaml);
    }

    private final Yaml yaml;

    public SnakeYamlConfigSerializer(Yaml yaml) {
        this.yaml = yaml;
    }

    @Override
    public Map<String, Object> toMap(String config) throws SerializationException {
        return this.yaml.load(config);
    }

    @Override
    public <T> T toObject(Class<T> configClass, String config) throws SerializationException {
        return this.yaml.loadAs(config, configClass);
    }

    @Override
    public String toString(Object config) throws SerializationException {
        return this.yaml.dumpAs(config, Tag.MAP, null);
    }
}
