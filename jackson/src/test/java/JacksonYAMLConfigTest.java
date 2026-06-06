import io.github.blockneko11.config.unified.api.IConfig;
import io.github.blockneko11.config.unified.api.IConfigHolder;
import io.github.blockneko11.config.unified.api.source.IConfigSource;
import io.github.blockneko11.config.unified.exception.ConfigException;
import io.github.blockneko11.config.unified.impl.Config;
import io.github.blockneko11.config.unified.impl.ConfigHolder;
import io.github.blockneko11.config.unified.impl.JacksonConfigSerializer;
import io.github.blockneko11.config.unified.impl.source.SimpleConfigSource;
import org.junit.jupiter.api.Test;

public class JacksonYAMLConfigTest {
    private static final IConfigSource SOURCE_1 = new SimpleConfigSource(() -> "{\n" +
            "  \"bool\": true,\n" +
            "  \"int\": 1,\n" +
            "  \"long\": 2,\n" +
            "  \"float\": 3.0,\n" +
            "  \"double\": 4.0,\n" +
            "  \"string\": \"5\"\n" +
            "}", System.out::println);

    @Test
    void load() throws ConfigException {
        IConfig config = new Config();
        config.load(SOURCE_1, JacksonConfigSerializer.DEFAULT_YAML);
        System.out.println(config.getBool("bool"));
        System.out.println(config.getInt("int"));
        System.out.println(config.getLong("long"));
        System.out.println(config.getFloat("float"));
        System.out.println(config.getDouble("double"));
        System.out.println(config.getString("string"));
    }

    @Test
    void save() throws ConfigException {
        IConfig config = new Config();
        config.addBool("bool", true);
        config.addInt("int", 1);
        config.addLong("long", 2L);
        config.addFloat("float", 3.0f);
        config.addDouble("double", 4.0);
        config.addString("string", "5");
        config.save(SOURCE_1, JacksonConfigSerializer.DEFAULT_YAML);
    }

    private static final IConfigSource SOURCE_2 = new SimpleConfigSource(() -> "{\n" +
            "  \"score\": 100,\n" +
            "  \"timestamp\": 100000000,\n" +
            "  \"temperature\": 36.5,\n" +
            "  \"distance\": 100.11451419,\n" +
            "  \"debug\": true,\n" +
            "  \"name\": \"George\",\n" +
            "  \"gender\": \"MALE\",\n" +
            "  \"address\": \"Beijing, China\"\n" +
            "}", System.out::println);

    @Test
    void holderLoad() throws ConfigException {
        IConfigHolder<TestBean> holder = new ConfigHolder<>(TestBean.class);
        System.out.println(holder.isPresent());
        holder.load(SOURCE_2, JacksonConfigSerializer.DEFAULT_YAML);
        System.out.println(holder.isPresent());
        System.out.println(holder.get());
    }

    @Test
    void holderSave() throws ConfigException {
        IConfigHolder<TestBean> holder = new ConfigHolder<>(TestBean.class);
        System.out.println(holder.isPresent());
        holder.set(TestBean.getInstance());
        System.out.println(holder.isPresent());
        holder.save(SOURCE_2, JacksonConfigSerializer.DEFAULT_YAML);
    }
}
