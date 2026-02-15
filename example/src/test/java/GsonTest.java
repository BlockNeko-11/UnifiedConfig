import io.github.blockneko11.config.unified.Config;
import io.github.blockneko11.config.unified.holder.ObjectConfigHolder;
import io.github.blockneko11.config.unified.conversion.Convertors;
import io.github.blockneko11.config.unified.exception.ConfigException;
import io.github.blockneko11.config.unified.gson.GsonSerializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GsonTest {
    private static final String CONFIG = "{\n" +
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

    @BeforeEach
    void setup() {
        Convertors.register(new UUIDConvertor());
    }

    @Test
    void read() throws ConfigException {
        ObjectConfigHolder<TestBean> binder = Config.bind(GsonSerializer.DEFAULT, TestBean.class);
        binder.deserialize(CONFIG);
        System.out.println(binder.get());
    }

    @Test
    void write() throws ConfigException {
        ObjectConfigHolder<TestBean> binder = Config.bind(GsonSerializer.DEFAULT, TestBean.class);
        binder.set(TestBean.getInstance());
        System.out.println(binder.serialize());
    }
}
