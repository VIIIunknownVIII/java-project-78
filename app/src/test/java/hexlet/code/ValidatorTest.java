package hexlet.code;

import hexlet.code.schemas.BaseSchema;
import hexlet.code.schemas.MapSchema;
import org.junit.jupiter.api.Test;
import java.util.Map;
import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ValidatorTest {
    @Test
    public void testStringSchema() {
        var v = new Validator();

        var schema = v.string();

        assertTrue(schema.isValid("")); // true
        assertTrue(schema.isValid(null)); // true

        schema.required(); // checks enabled

        assertFalse(schema.isValid(null)); // false
        assertFalse(schema.isValid("")); // false
        assertTrue(schema.isValid("what does the fox say")); // true
        assertTrue(schema.isValid("hexlet")); // true

        assertTrue(schema.contains("wh").isValid("what does the fox say")); // true
        assertTrue(schema.contains("what").isValid("what does the fox say")); // true
        assertFalse(schema.contains("what the").isValid("what does the fox say")); // false

        assertFalse(schema.isValid("what does the fox say")); // false

        var schema1 = v.string();
        assertTrue(schema1.minLength(10).minLength(4).isValid("Hexlet")); // true
    }

    @Test
    public void testNumberSchema() {
        var v = new Validator();

        var schema = v.number();

        assertTrue(schema.isValid(5)); // true

        assertTrue(schema.isValid(null)); // true
        assertTrue(schema.positive().isValid(null)); // true

        schema.required(); // checks enabled

        assertFalse(schema.isValid(null)); // false
        assertTrue(schema.isValid(10)); // true

        assertFalse(schema.isValid(-10)); // false
        assertFalse(schema.isValid(0)); // false

        schema.range(5, 10);

        assertTrue(schema.isValid(5)); // true
        assertTrue(schema.isValid(10)); // true
        assertFalse(schema.isValid(4)); // false
        assertFalse(schema.isValid(11)); // false
    }

    @Test
    public void testMapSchema() {
        var v = new Validator();

        var schema = v.map();

        assertTrue(schema.isValid(null)); // true

        schema.required(); // checks enabled

        assertFalse(schema.isValid(null)); // false
        assertTrue(schema.isValid(new HashMap<>())); // true
        var data = new HashMap<String, String>();
        data.put("key1", "value1");
        assertTrue(schema.isValid(data)); // true

        schema.sizeof(2);

        assertFalse(schema.isValid(data));  // false
        data.put("key2", "value2");
        assertTrue(schema.isValid(data)); // true

        var schema1 = v.map();
        assertTrue(schema1.sizeof(9).isValid(null)); // true
    }

    @Test
    public void testStringShapeSchemas() {
        var v = new Validator();

        var schema = v.map();

        Map<String, BaseSchema<String>> schemas = new HashMap<>();

        schemas.put("firstName", v.string().required());
        schemas.put("lastName", v.string().required().minLength(2));

        schema.shape(schemas); // shape() method enables all checks as required() method

        Map<String, String> human1 = new HashMap<>();
        human1.put("firstName", "John");
        human1.put("lastName", "Smith");
        assertTrue(schema.isValid(human1)); // true

        Map<String, String> human2 = new HashMap<>();
        human2.put("firstName", "John");
        human2.put("lastName", null);
        assertFalse(schema.isValid(human2)); // false

        Map<String, String> human3 = new HashMap<>();
        human3.put("firstName", "Anna");
        human3.put("lastName", "B");
        assertFalse(schema.isValid(human3)); // false
    }

    @Test
    public void testNumberShapeSchemas() {
        var v = new Validator();

        var schema = v.map();

        Map<String, BaseSchema<Integer>> schemas = new HashMap<>();

        schemas.put("score", v.number().required());
        schemas.put("point", v.number().required().positive());

        schema.shape(schemas); // shape() method enables all checks as required() method

        Map<String, Integer> human1 = new HashMap<>();
        human1.put("score", 2);
        human1.put("point", 9);
        assertTrue(schema.isValid(human1)); // true

        Map<String, Integer> human2 = new HashMap<>();
        human2.put("score", 2);
        human2.put("point", null);
        assertFalse(schema.isValid(human2)); // false

        Map<String, Integer> human3 = new HashMap<>();
        human3.put("score", 2);
        human3.put("point", -9);
        assertFalse(schema.isValid(human3)); // false
    }

    @Test
    public void testMixedShapeSchemas() {
        var v = new Validator();
        Map<String, BaseSchema> schemas = new HashMap<>();

        schemas.put("name", v.string().required());
        schemas.put("age", v.number().required().positive());

        MapSchema schema = v.map().sizeof(2).shape(schemas);

        Map<String, Object> human1 = new HashMap<>();
        human1.put("name", "Pepe");
        human1.put("age", 29);
        assertTrue(schema.isValid(human1)); // true

        Map<String, Object> human2 = new HashMap<>();
        human2.put("name", "");
        human2.put("age", 29);
        assertFalse(schema.isValid(human2)); // false

        Map<String, Object> human3 = new HashMap<>();
        human3.put("name", "Pepe");
        human3.put("age", -29);
        assertFalse(schema.isValid(human3)); // false

        Map<String, Object> human4 = new HashMap<>();
        human4.put("name", "Pepe");
        assertFalse(schema.isValid(human4)); // false
    }
}
