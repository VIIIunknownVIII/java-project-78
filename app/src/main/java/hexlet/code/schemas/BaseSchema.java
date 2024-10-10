package hexlet.code.schemas;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Predicate;

public abstract class BaseSchema<T> {
    protected Map<String, Predicate<T>> checks = new LinkedHashMap<>();
    protected boolean isRequired = false;

    protected final void addCheck(String name, Predicate<T> validate) {
        checks.put(name, validate);
    }
    protected final void setRequired() {
        isRequired = true;
    }

    public final boolean isValid(T value) {
        if (!isRequired) {
            return true;
        }

        if (value == null) {
            return false;
        }

        for (String key : checks.keySet()) {
            Predicate<T> check = checks.get(key);
            if (!check.test(value)) {
                return false;
            }
        }

        return true;
    }
}
