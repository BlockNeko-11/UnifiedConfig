package io.github.blockneko11.config.unified;

import io.github.blockneko11.config.unified.holder.ObjectConfigHolder;
import io.github.blockneko11.config.unified.holder.MapConfigHolder;
import io.github.blockneko11.config.unified.io.FileObjectConfigHolder;
import io.github.blockneko11.config.unified.io.FileMapConfigHolder;
import io.github.blockneko11.config.unified.serialization.Serializer;

import java.io.File;

public interface Config {
    static MapConfigHolder map(Serializer serializer) {
        return new MapConfigHolder(serializer);
    }

    static FileMapConfigHolder map(Serializer serializer, File file) {
        return new FileMapConfigHolder(serializer, file);
    }

    static <T> ObjectConfigHolder<T> bind(Serializer serializer, Class<T> clazz) {
        return new ObjectConfigHolder<>(serializer, clazz);
    }

    static <T> FileObjectConfigHolder<T> bind(Serializer serializer, Class<T> clazz, File file) {
        return new FileObjectConfigHolder<>(serializer, clazz, file);
    }
}
