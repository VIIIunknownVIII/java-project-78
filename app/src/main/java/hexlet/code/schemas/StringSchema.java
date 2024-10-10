package hexlet.code.schemas;

public final class StringSchema extends BaseSchema<String> {
    public StringSchema() { }

    public StringSchema required() {
        addCheck(
                "required",
                value -> !value.isEmpty()
        );
        setRequired();
        return this;
    }

    public StringSchema minLength(int length) {
        addCheck(
                "minLength",
                value -> value.length() >= length
        );
        return this;
    }

    public StringSchema contains(String subStr) {
        addCheck(
                "contains",
                value -> value.contains(subStr)
        );
        return this;
    }
}
