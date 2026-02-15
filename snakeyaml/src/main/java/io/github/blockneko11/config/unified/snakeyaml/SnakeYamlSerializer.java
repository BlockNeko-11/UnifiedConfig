package io.github.blockneko11.config.unified.snakeyaml;

import io.github.blockneko11.config.unified.serialization.Serializer;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.nodes.Tag;

import java.util.Map;

public class SnakeYamlSerializer implements Serializer {
    public static final Yaml DEFAULT_YAML = new Yaml(getDefaultLoaderOptions(), getDefaultDumperOptions());
    public static final SnakeYamlSerializer DEFAULT = new SnakeYamlSerializer(DEFAULT_YAML);

    private final Yaml yaml;

    public SnakeYamlSerializer(Yaml yaml) {
        this.yaml = yaml;
    }

    @Override
    public Map<String, Object> deserialize(String config) {
        return this.yaml.load(config);
    }

    @Override
    public String serialize(Map<String, Object> config) {
        return this.yaml.dumpAs(config, Tag.MAP, null);
    }

    public static LoaderOptions getDefaultLoaderOptions() {
        LoaderOptions options = new LoaderOptions();

        options.setProcessComments(false);

        return options;
    }

    public static DumperOptions getDefaultDumperOptions() {
        DumperOptions options = new DumperOptions();

        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setProcessComments(false);
        options.setIndent(2);
        options.setIndicatorIndent(2);
        options.setIndentWithIndicator(true);
        options.setPrettyFlow(false);

        return options;
    }
}
