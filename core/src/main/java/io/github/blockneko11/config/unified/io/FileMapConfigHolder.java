package io.github.blockneko11.config.unified.io;

import io.github.blockneko11.config.unified.holder.MapConfigHolder;
import io.github.blockneko11.config.unified.serialization.Serializer;
import io.github.blockneko11.config.unified.util.io.IOUtils;

import java.io.File;

public class FileMapConfigHolder extends MapConfigHolder {
    private final File file;

    public FileMapConfigHolder(Serializer serializer, File file) {
        super(serializer);
        this.file = file;
    }

    public void loadFile() {
        super.deserialize(IOUtils.readFile(this.file));
    }

    public void saveFile() {
        IOUtils.writeFile(this.file, super.serialize());
    }
}
