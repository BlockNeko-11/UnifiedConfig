public class TestBean {
    public int score;
    public long timestamp;
    public float temperature;
    public double distance;
    public boolean debug;

    public String name;

    public enum Gender {
        MALE,
        FEMALE
    }

    public Gender gender;

    public transient int transientField;

    public String address;

    @Override
    public String toString() {
        return "TestBean{" + "score=" + score +
                ", timestamp=" + timestamp +
                ", temperature=" + temperature +
                ", distance=" + distance +
                ", debug=" + debug +
                ", name='" + name + '\'' +
                ", gender=" + gender +
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

        testBean.gender = Gender.MALE;
        testBean.transientField = 114514;
        testBean.address = "Beijing, China";

        return testBean;
    }
}
