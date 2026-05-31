package io.github.blockneko11.config.unified.impl;

import io.github.blockneko11.config.unified.exception.SerializationException;
import io.github.blockneko11.config.unified.api.IConfigSerializer;
import io.github.blockneko11.config.unified.util.Util;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.nodes.Tag;

import java.util.Map;

public class SnakeYamlConfigSerializer implements IConfigSerializer {
    public static final Yaml DEFAULT_YAML = new Yaml(Util.withInitialize(new LoaderOptions(), ops -> {
            ops.setProcessComments(false);
        }), Util.withInitialize(new DumperOptions(), ops -> {
            ops.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
            ops.setProcessComments(false);
            ops.setIndent(2);
            ops.setIndicatorIndent(2);
            ops.setIndentWithIndicator(true);
            ops.setPrettyFlow(false);
        }));
    public static final SnakeYamlConfigSerializer DEFAULT = new SnakeYamlConfigSerializer(DEFAULT_YAML);

    private final Yaml yaml;

    public SnakeYamlConfigSerializer(Yaml yaml) {
        this.yaml = yaml;
    }

    @Override
    public Map<String, Object> toMap(String config) throws SerializationException {
        return this.yaml.load(config);
    }

    @Override
    public String toString(Map<String, Object> config) throws SerializationException {
        return this.yaml.dumpAs(config, Tag.MAP, null);
    }
}
