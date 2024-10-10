package hexlet.code.schemas;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public abstract class BaseSchema<T> {
    private final List<Predicate<T>> checks = new ArrayList<>();
    private boolean isRequired = false;
    
    public BaseSchema<T> required() {
        isRequired = true;
        checks.add(value -> value != null);
        return this;
    }

    protected void addCheck(Predicate<T> predicate) {
        checks.add(predicate);
    }

    @SuppressWarnings("unchecked")
    public boolean isValid(Object value) {
        if (!isRequired && value == null) {
            return true;
        }
        try {
            return checks.stream().allMatch(check -> check.test((T) value));
        } catch (ClassCastException e) {
            return false; // Если произошла ошибка приведения типов, возвращаем false
        }
    }
}

