package hexlet.code.schemas;

public final class NumberSchema extends BaseSchema<Integer> {
    public NumberSchema() { }

    public NumberSchema required() {
        setRequired();
        return this;
    }

    public NumberSchema positive() {
        addCheck(
                "positive",
                value -> value > 0
        );
        return this;
    }

    public NumberSchema range(Integer min, Integer max) {
        addCheck(
                "range",
                value -> value >= min && value <= max
        );
        return this;
    }
}
