import io.github.blockneko11.config.unified.api.IConfig;
import io.github.blockneko11.config.unified.api.source.IConfigSource;
import io.github.blockneko11.config.unified.exception.ConfigException;
import io.github.blockneko11.config.unified.impl.Config;
import io.github.blockneko11.config.unified.impl.GsonConfigSerializer;
import io.github.blockneko11.config.unified.impl.source.ConfigSource;
import org.junit.jupiter.api.Test;

public class GsonConfigTest {
    private static final IConfigSource SOURCE = new ConfigSource(() -> "{\n" +
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
        config.load(SOURCE, GsonConfigSerializer.DEFAULT);
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
        config.save(SOURCE, GsonConfigSerializer.DEFAULT);
    }
}
