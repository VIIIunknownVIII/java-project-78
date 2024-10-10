package hexlet.code.schemas;

import java.util.Map;

public final class MapSchema<T> extends BaseSchema<Map<String, T>> {
    public MapSchema<T> required() {
        setRequired();
        return this;
    }

    public MapSchema<T> sizeof(int size) {
        addCheck(
                "sizeof",
                value -> value.size() == size
        );
        return this;
    }

    public MapSchema<T> shape(Map<String, BaseSchema<T>> schemas) {
        addCheck(
                "shape",
                map -> {
                    for (String key : schemas.keySet()) {
                        var schema = schemas.get(key);
                        T value = map.get(key);

                        if (!schema.isValid(value)) {
                            return false;
                        }
                    }

                    return true;
                }
        );
        setRequired();
        return this;
    }
}
