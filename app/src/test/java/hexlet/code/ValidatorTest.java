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
        var validator = new Validator();

        var strSchema = validator.string();

        assertTrue(strSchema.isValid(""));
        assertTrue(strSchema.isValid(null));

        strSchema.required();

        assertFalse(strSchema.isValid(null));
        assertFalse(strSchema.isValid(""));
        assertTrue(strSchema.isValid("what does the fox say"));
        assertTrue(strSchema.isValid("hexlet"));

        assertTrue(strSchema.contains("wh").isValid("what does the fox say"));
        assertTrue(strSchema.contains("what").isValid("what does the fox say"));
        assertFalse(strSchema.contains("what the").isValid("what does the fox say"));

        assertFalse(strSchema.isValid("what does the fox say"));

        var anotherStrSchema = validator.string();
        assertTrue(anotherStrSchema.minLength(10).minLength(4).isValid("Hexlet"));
    }

    @Test
    public void testNumberSchema() {
        var validator = new Validator();

        var numSchema = validator.number();

        assertTrue(numSchema.isValid(5));

        assertTrue(numSchema.isValid(null));
        assertTrue(numSchema.positive().isValid(null));

        numSchema.required();

        assertFalse(numSchema.isValid(null));
        assertTrue(numSchema.isValid(10));

        assertFalse(numSchema.isValid(-10));
        assertFalse(numSchema.isValid(0));

        numSchema.range(5, 10);

        assertTrue(numSchema.isValid(5));
        assertTrue(numSchema.isValid(10));
        assertFalse(numSchema.isValid(4));
        assertFalse(numSchema.isValid(11));
    }

    @Test
    public void testMapSchema() {
        var validator = new Validator();

        var mapSchema = validator.map();

        assertTrue(mapSchema.isValid(null));

        mapSchema.required();

        assertFalse(mapSchema.isValid(null));
        assertTrue(mapSchema.isValid(new HashMap<>()));
        var mapData = new HashMap<String, String>();
        mapData.put("key1", "value1");
        assertTrue(mapSchema.isValid(mapData));

        mapSchema.sizeof(2);

        assertFalse(mapSchema.isValid(mapData));
        mapData.put("key2", "value2");
        assertTrue(mapSchema.isValid(mapData));

        var anotherMapSchema = validator.map();
        assertTrue(anotherMapSchema.sizeof(9).isValid(null));
    }

    @Test
    public void testStringShapeSchemas() {
        var validator = new Validator();

        var mapSchema = validator.map();

        Map<String, BaseSchema<String>> stringSchemas = new HashMap<>();

        stringSchemas.put("firstName", validator.string().required());
        stringSchemas.put("lastName", validator.string().required().minLength(2));

        mapSchema.shape(stringSchemas);

        Map<String, String> human1 = new HashMap<>();
        human1.put("firstName", "John");
        human1.put("lastName", "Smith");
        assertTrue(mapSchema.isValid(human1));

        Map<String, String> human2 = new HashMap<>();
        human2.put("firstName", "John");
        human2.put("lastName", null);
        assertFalse(mapSchema.isValid(human2));

        Map<String, String> human3 = new HashMap<>();
        human3.put("firstName", "Anna");
        human3.put("lastName", "B");
        assertFalse(mapSchema.isValid(human3));
    }

    @Test
    public void testNumberShapeSchemas() {
        var validator = new Validator();

        var mapSchema = validator.map();

        Map<String, BaseSchema<Integer>> numberSchemas = new HashMap<>();

        numberSchemas.put("score", validator.number().required());
        numberSchemas.put("point", validator.number().required().positive());

        mapSchema.shape(numberSchemas);

        Map<String, Integer> human1 = new HashMap<>();
        human1.put("score", 2);
        human1.put("point", 9);
        assertTrue(mapSchema.isValid(human1));

        Map<String, Integer> human2 = new HashMap<>();
        human2.put("score", 2);
        human2.put("point", null);
        assertFalse(mapSchema.isValid(human2));

        Map<String, Integer> human3 = new HashMap<>();
        human3.put("score", 2);
        human3.put("point", -9);
        assertFalse(mapSchema.isValid(human3));
    }

    @Test
    public void testMixedShapeSchemas() {
        var validator = new Validator();
        Map<String, BaseSchema> mixedSchemas = new HashMap<>();

        mixedSchemas.put("name", validator.string().required());
        mixedSchemas.put("age", validator.number().required().positive());

        MapSchema mapSchema = validator.map().sizeof(2).shape(mixedSchemas);

        Map<String, Object> human1 = new HashMap<>();
        human1.put("name", "Pepe");
        human1.put("age", 29);
        assertTrue(mapSchema.isValid(human1));

        Map<String, Object> human2 = new HashMap<>();
        human2.put("name", "");
        human2.put("age", 29);
        assertFalse(mapSchema.isValid(human2));

        Map<String, Object> human3 = new HashMap<>();
        human3.put("name", "Pepe");
        human3.put("age", -29);
        assertFalse(mapSchema.isValid(human3));

        Map<String, Object> human4 = new HashMap<>();
        human4.put("name", "Pepe");
        assertFalse(mapSchema.isValid(human4));
    }
}
