package io.github.blockneko11.config.unified.validation;

import io.github.blockneko11.config.unified.exception.ConfigException;
import io.github.blockneko11.config.unified.exception.ValidationException;
import io.github.blockneko11.config.unified.util.ConstructorUtil;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public final class Validations {
    public static void validateNull(Field field) throws ValidationException {
        if (field.isAnnotationPresent(NonNull.class)) {
            throw new ValidationException("field " + field.getName() + " is not nullable");
        }
    }

    public static void validateInt(Field field, int value) throws ValidationException {
        NumberRange.IntRange range = field.getAnnotation(NumberRange.IntRange.class);
        if (range != null) {
            if (value < range.min() || value > range.max()) {
                throw new ValidationException("field " + field.getName() + " is out of range");
            }
        }
    }

    public static void validateLong(Field field, long value) throws ValidationException {
        NumberRange.LongRange range = field.getAnnotation(NumberRange.LongRange.class);
        if (range != null) {
            if (value < range.min() || value > range.max()) {
                throw new ValidationException("field " + field.getName() + " is out of range");
            }
        }
    }

    public static void validateFloat(Field field, float value) throws ValidationException {
        NumberRange.FloatRange range = field.getAnnotation(NumberRange.FloatRange.class);
        if (range != null) {
            if (value < range.min() || value > range.max()) {
                throw new ValidationException("field " + field.getName() + " is out of range");
            }
        }
    }

    public static void validateDouble(Field field, double value) throws ValidationException {
        NumberRange.DoubleRange range = field.getAnnotation(NumberRange.DoubleRange.class);
        if (range != null) {
            if (value < range.min() || value > range.max()) {
                throw new ValidationException("field " + field.getName() + " is out of range");
            }
        }
    }

    public static void validateString(Field field, String value) throws ConfigException {
        StringValidator.InArray inArray = field.getAnnotation(StringValidator.InArray.class);
        if (inArray != null) {
            List<String> list = Arrays.asList(inArray.value());
            if (!list.contains(value)) {
                throw new ValidationException("field " + field.getName() + " is not in specific value. Specific values: " + list);
            }
        }

        StringValidator.Regex regex = field.getAnnotation(StringValidator.Regex.class);
        if (regex != null) {
            if (!value.matches(regex.value())) {
                throw new ValidationException("field " + field.getName() + " does not match regex " + regex.value());
            }
        }

        validateObject(field, value);
    }

    public static void validateObject(Field field, Object value) throws ConfigException {
        Validator validator = field.getAnnotation(Validator.class);
        if (validator != null) {
            Class<? extends Predicate<Object>> predicate = (Class<? extends Predicate<Object>>) validator.value();
            Predicate<Object> instance = ConstructorUtil.newInstance(predicate);
            if (!instance.test(value)) {
                throw new ValidationException("field " + field.getName() + " is not valid");
            }
        }
    }

    private Validations() {
    }
}
