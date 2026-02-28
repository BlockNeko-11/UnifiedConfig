package io.github.blockneko11.config.unified.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public final class NumberRange {
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface IntRange {
        int min() default Integer.MIN_VALUE;
        int max() default Integer.MAX_VALUE;
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface LongRange {
        long min() default Long.MIN_VALUE;
        long max() default Long.MAX_VALUE;
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface FloatRange {
        float min() default Float.MIN_VALUE;
        float max() default Float.MAX_VALUE;
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface DoubleRange {
        double min() default Double.MIN_VALUE;
        double max() default Double.MAX_VALUE;
    }
}
