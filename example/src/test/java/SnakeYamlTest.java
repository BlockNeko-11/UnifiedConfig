import io.github.blockneko11.config.unified.reflect.ReflectiveConfigHolder;
import io.github.blockneko11.config.unified.snakeyaml.SnakeYamlConfigSerializer;
import io.github.blockneko11.config.unified.source.StringConfigSource;
import org.junit.jupiter.api.Test;

public class SnakeYamlTest {
    private static final String CONFIG = "score: 100\n" +
            "timestamp: 100000000\n" +
            "temperature: 36.5\n" +
            "distance: 100.11451419\n" +
            "debug: true\n" +
            "name: George\n" +
            "hobby:\n" +
            "  - Coding\n" +
            "  - Gaming\n" +
            "info:\n" +
            "  age: '18'\n" +
            "  height: '180'\n" +
            "  weight: '100000'\n" +
            "gender: MALE\n" +
            "uuid: 11111111-1111-1111-1111-111111111111\n" +
            "add: Beijing, China";

    @Test
    void read() throws Exception {
        ReflectiveConfigHolder<TestBean> binder = ReflectiveConfigHolder.builder(TestBean.class)
                .serializer(SnakeYamlConfigSerializer.DEFAULT)
                .source(new StringConfigSource(() -> CONFIG, System.out::println))
                .build();
        binder.load();
        System.out.println(binder.get());
    }

    @Test
    void write() throws Exception {
        ReflectiveConfigHolder<TestBean> binder = ReflectiveConfigHolder.builder(TestBean.class)
                .serializer(SnakeYamlConfigSerializer.DEFAULT)
                .source(new StringConfigSource(() -> CONFIG, System.out::println))
                .build();
        binder.set(TestBean.getInstance());
        binder.save();
    }
}
