import io.github.blockneko11.config.unified.core.BoundConfig;
import io.github.blockneko11.config.unified.conversion.Convertors;
import io.github.blockneko11.config.unified.gson.GsonSerializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GsonTest {
    private static final String CONFIG_1 = "{\n" +
            "  \"score\": 100,\n" +
            "  \"timestamp\": 100000000,\n" +
            "  \"temperature\": 36.5,\n" +
            "  \"distance\": 100.11451419,\n" +
            "  \"debug\": true,\n" +
            "  \"name\": \"George\",\n" +
            "  \"hobby\": [\n" +
            "    \"Coding\",\n" +
            "    \"Gaming\"\n" +
            "  ],\n" +
            "  \"info\": {\n" +
            "    \"age\": \"18\",\n" +
            "    \"height\": \"180\",\n" +
            "    \"weight\": \"100000\"\n" +
            "  },\n" +
            "  \"gender\": \"MALE\",\n" +
            "  \"uuid\": \"11111111-1111-1111-1111-111111111111\",\n" +
            "  \"add\": \"Beijing, China\"\n" +
            "}";

    private static final String CONFIG_2 = "{\n" +
            "  \"inner\": {\n" +
            "    \"name\": \"222\"\n" +
            "  }\n" +
            "}";

    @BeforeEach
    void setup() {
        Convertors.register(new UUIDConvertor());
    }

    @Test
    void read() {
        BoundConfig<TestBean> c1 = BoundConfig.builder(TestBean.class)
                .serializer(GsonSerializer.DEFAULT)
                .loadingAction(() -> CONFIG_1)
                .build();
        c1.load();
        System.out.println(c1.get());

        BoundConfig<Outer> c2 = BoundConfig.builder(Outer.class)
                .serializer(GsonSerializer.DEFAULT)
                .loadingAction(() -> CONFIG_2)
                .build();
        c2.load();
        System.out.println(c2.get());
    }

    @Test
    void write() {
        BoundConfig<TestBean> c1 = BoundConfig.builder(TestBean.class)
                .serializer(GsonSerializer.DEFAULT)
                .savingAction(System.out::println)
                .build();
        c1.set(TestBean.getInstance());
        c1.save();

        BoundConfig<Outer> c2 = BoundConfig.builder(Outer.class)
                .serializer(GsonSerializer.DEFAULT)
                .savingAction(System.out::println)
                .build();
        c2.set(new Outer());
        c2.save();
    }
}
