import io.github.blockneko11.config.unified.conversion.Convert;
import io.github.blockneko11.config.unified.property.Id;
import io.github.blockneko11.config.unified.property.Ignore;

import java.util.*;

public class TestBean {
    public int score;
    public long timestamp;
    public float temperature;
    public double distance;
    public boolean debug;
    public String name;
    public List<String> hobby;
    public Map<String, String> info;

    public enum Gender {
        MALE,
        FEMALE
    }

    public Gender gender;

    @Convert
    public UUID uuid;

    @Ignore
    public int transientField;

    public String address;

    @Override
    public String toString() {
        return "TestBean{" + "score=" + score +
                ", timestamp=" + timestamp +
                ", temperature=" + temperature +
                ", distance=" + distance +
                ", debug=" + debug +
                ", name='" + name + '\'' +
                ", hobby=" + hobby +
                ", info=" + info +
                ", gender=" + gender +
                ", uuid=" + uuid +
                ", transientField=" + transientField +
                ", address='" + address + '\'' +
                '}';
    }

    public static TestBean getInstance() {
        TestBean testBean = new TestBean();
        testBean.score = 100;
        testBean.timestamp = 100000000L;
        testBean.temperature = 36.5f;
        testBean.distance = 100.11451419d;
        testBean.debug = true;
        testBean.name = "George";
        testBean.hobby = new ArrayList<>();
        testBean.hobby.add("Coding");
        testBean.hobby.add("Gaming");

        testBean.info = new LinkedHashMap<>();
        testBean.info.put("age", "18");
        testBean.info.put("height", "180");
        testBean.info.put("weight", "100000");

        testBean.gender = Gender.MALE;
        testBean.uuid = UUID.fromString("11111111-1111-1111-1111-111111111111");
        testBean.transientField = 114514;
        testBean.address = "Beijing, China";

        return testBean;
    }
}
