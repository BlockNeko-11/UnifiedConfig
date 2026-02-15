import io.github.blockneko11.config.unified.conversion.Convertor;

import java.util.UUID;

public class UUIDConvertor implements Convertor<UUID> {
    @Override
    public Class<UUID> getConvertedType() {
        return UUID.class;
    }

    @Override
    public UUID deserialize(Object config) {
        return UUID.fromString((String) config);
    }

    @Override
    public Object serialize(UUID field) {
        return field.toString();
    }
}
