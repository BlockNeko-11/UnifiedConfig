import io.github.blockneko11.config.unified.exception.ConfigException;
import io.github.blockneko11.config.unified.reflect.ReflectiveConfigHolder;
import io.github.blockneko11.config.unified.gson.GsonConfigSerializer;
import io.github.blockneko11.config.unified.source.StringConfigSource;
import org.junit.jupiter.api.Test;

import java.util.UUID;

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

    @Test
    void read() throws Exception {
        ReflectiveConfigHolder<TestBean> c1 = ReflectiveConfigHolder.builder(TestBean.class)
                .serializer(GsonConfigSerializer.DEFAULT)
                .source(new StringConfigSource(() -> CONFIG_1, System.out::println))
                .build();
        c1.load();
        System.out.println(c1.get());

        ReflectiveConfigHolder<Outer> c2 = ReflectiveConfigHolder.builder(Outer.class)
                .serializer(GsonConfigSerializer.DEFAULT)
                .source(new StringConfigSource(() -> CONFIG_2, System.out::println))
                .build();
        c2.load();
        System.out.println(c2.get());
    }

    @Test
    void write() throws Exception {
        ReflectiveConfigHolder<TestBean> c1 = ReflectiveConfigHolder.builder(TestBean.class)
                .serializer(GsonConfigSerializer.DEFAULT)
                .source(new StringConfigSource(() -> CONFIG_1, System.out::println))
                .build();
        TestBean bean = TestBean.getInstance();
        c1.set(bean);
        c1.save();

        try {
            bean.score++;
            c1.save();
        } catch (ConfigException e) {
            e.printStackTrace();
        }

        try {
            bean.score--;
            bean.name = "AAAAAAAAAAAAAAAAAA";
            c1.save();
        } catch (ConfigException e) {
            e.printStackTrace();
        }

        try {
            bean.name = "George";
            bean.address = "";
            c1.save();
        } catch (ConfigException e) {
            e.printStackTrace();
        }

        try {
            bean.address = "Beijing, China";
            bean.uuid = UUID.fromString("22222222-2222-2222-2222-222222222222");
            c1.save();
        } catch (ConfigException e) {
            e.printStackTrace();
        }

        ReflectiveConfigHolder<Outer> c2 = ReflectiveConfigHolder.builder(Outer.class)
                .serializer(GsonConfigSerializer.DEFAULT)
                .source(new StringConfigSource(() -> CONFIG_2, System.out::println))
                .build();
        c2.set(new Outer());
        c2.save();
    }
}
