package io.github.blockneko11.config.unified.validation;

import io.github.blockneko11.config.unified.exception.ConfigException;
import io.github.blockneko11.config.unified.exception.ValidationException;
import io.github.blockneko11.config.unified.util.ConstructorUtil;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public final class Validations {
    private static final Map<Class<? extends Predicate<?>>, Predicate<?>> VALIDATORS = new HashMap<>();

    public static void validateNull(Field field) throws ValidationException {
        if (field.isAnnotationPresent(NonNull.class)) {
            throw new ValidationException("field " + field.getName() + " is not nullable");
        }
    }

    public static void validateInt(Field field, int value) throws ValidationException {
        NumberRange.IntRange range = field.getAnnotation(NumberRange.IntRange.class);
        if (range != null) {
            if (value < range.min() || value > range.max()) {
                throw new ValidationException("int field " + field.getName() + " is out of range (" + range.min() + ", " + range.max() + ")");
            }
        }
    }

    public static void validateLong(Field field, long value) throws ValidationException {
        NumberRange.LongRange range = field.getAnnotation(NumberRange.LongRange.class);
        if (range != null) {
            if (value < range.min() || value > range.max()) {
                throw new ValidationException("long field " + field.getName() + " is out of range (" + range.min() + ", " + range.max() + ")");
            }
        }
    }

    public static void validateFloat(Field field, float value) throws ValidationException {
        NumberRange.FloatRange range = field.getAnnotation(NumberRange.FloatRange.class);
        if (range != null) {
            if (value < range.min() || value > range.max()) {
                throw new ValidationException("float field " + field.getName() + " is out of range (" + range.min() + ", " + range.max() + ")");
            }
        }
    }

    public static void validateDouble(Field field, double value) throws ValidationException {
        NumberRange.DoubleRange range = field.getAnnotation(NumberRange.DoubleRange.class);
        if (range != null) {
            if (value < range.min() || value > range.max()) {
                throw new ValidationException("double field " + field.getName() + " is out of range (" + range.min() + ", " + range.max() + ")");
            }
        }
    }

    public static void validateString(Field field, String value) throws ConfigException {
        StringValidation.InArray inArray = field.getAnnotation(StringValidation.InArray.class);
        if (inArray != null) {
            List<String> list = Arrays.asList(inArray.value());
            if (!list.contains(value)) {
                throw new ValidationException("string field " + field.getName() + " is not in specific value. Specific values: " + list);
            }
        }

        StringValidation.Regex regex = field.getAnnotation(StringValidation.Regex.class);
        if (regex != null) {
            if (!value.matches(regex.value())) {
                throw new ValidationException("string field " + field.getName() + " does not match regex " + regex.value());
            }
        }

        StringValidation.NonEmpty nonEmpty = field.getAnnotation(StringValidation.NonEmpty.class);
        if (nonEmpty != null) {
            if (value.isEmpty()) {
                throw new ValidationException("string field " + field.getName() + " is empty");
            }
        }

        StringValidation.Length length = field.getAnnotation(StringValidation.Length.class);
        int i = value.length();
        if (length != null) {
            int min = length.min();
            int max = length.max();
            if (i < min) {
                throw new ValidationException("the length of string field " + field.getName() + " is less than " + min);
            }

            if (i > max) {
                throw new ValidationException("the length of string field " + field.getName() + " is greater than " + max);
            }
        }

        validateObject(field, value);
    }

    public static void validateObject(Field field, Object value) throws ConfigException {
        Validator validator = field.getAnnotation(Validator.class);
        if (validator != null) {
            Class<? extends Predicate<?>> validatorClass = validator.value();
            if (!VALIDATORS.containsKey(validatorClass)) {
                VALIDATORS.put(validatorClass, ConstructorUtil.newInstance(validatorClass));
            }

            Predicate<Object> predicate = (Predicate<Object>) VALIDATORS.get(validatorClass);
            if (!predicate.test(value)) {
                throw new ValidationException("field " + field.getName() + " is not valid");
            }
        }
    }

    private Validations() {
    }
}
