import io.github.blockneko11.config.unified.conversion.ConfigConvertor;

import java.util.UUID;

public class UUIDConfigConvertor implements ConfigConvertor<UUID> {
    @Override
    public Class<UUID> getTargetType() {
        return UUID.class;
    }

    @Override
    public UUID toTarget(Object config) {
        return UUID.fromString((String) config);
    }

    @Override
    public Object toSerialized(UUID field) {
        return field.toString();
    }
}
