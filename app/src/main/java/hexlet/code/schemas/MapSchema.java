package hexlet.code.schemas;

import java.util.HashMap;
import java.util.Map;

public class MapSchema extends BaseSchema<Map<String, Object>> {

    private final Map<String, BaseSchema<?>> shapeSchemas = new HashMap<>();

    public MapSchema required() {
        super.required();
        addCheck(map -> map instanceof Map);
        return this;
    }

    public MapSchema shape(Map<String, BaseSchema<?>> schemas) {
        shapeSchemas.putAll(schemas);
        addCheck(map -> map != null && validateShape(map));
        return this;
    }

    private boolean validateShape(Map<String, Object> map) {
        for (Map.Entry<String, BaseSchema<?>> entry : shapeSchemas.entrySet()) {
            String key = entry.getKey();
            BaseSchema<?> schema = entry.getValue();
            if (map.containsKey(key)) {
                Object value = map.get(key);
                if (!schema.isValid(value)) { // Проблема может быть в том, что метод isValid возвращает неожиданный результат
                    return false;
                }
            }
        }
        return true;
    }
}
