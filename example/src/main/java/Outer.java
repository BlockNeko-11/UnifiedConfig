import io.github.blockneko11.config.unified.reflect.property.Nest;

public class Outer {
    @Nest
    public Inner inner = new Inner();

    public static class Inner {
        public String name = "111";

        @Override
        public String toString() {
            return "Inner{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Outer{" +
                "inner=" + inner +
                '}';
    }
}
