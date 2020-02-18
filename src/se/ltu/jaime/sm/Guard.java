package se.ltu.jaime.sm;

import java.util.Map;
import java.util.Objects;

public class Guard {
    private final String variable;
    private final Object value;

    public Guard(final String variable, final Object value) {
        this.variable = variable;
        this.value = value;
    }

    public boolean evaluate(final Map<String, Object> environment) {
        return Objects.equals(environment.get(variable), value);
    }
}
