package io.github.blockneko11.config.unified.io;

import io.github.blockneko11.config.unified.holder.ObjectConfigHolder;
import io.github.blockneko11.config.unified.exception.ConfigException;
import io.github.blockneko11.config.unified.serialization.Serializer;
import io.github.blockneko11.config.unified.util.io.IOUtils;

import java.io.File;

public class FileObjectConfigHolder<T> extends ObjectConfigHolder<T> {
    private final File file;

    public FileObjectConfigHolder(Serializer serializer, Class<T> clazz, File file) {
        super(serializer, clazz);
        this.file = file;
    }

    public void loadFile() throws ConfigException {
        super.deserialize(IOUtils.readFile(this.file));
    }

    public void saveFile() throws ConfigException {
        IOUtils.writeFile(this.file, super.serialize());
    }
}
