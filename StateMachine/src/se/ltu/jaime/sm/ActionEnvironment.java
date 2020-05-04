package se.ltu.jaime.sm;

import java.util.Map;
import java.util.Set;

public class ActionEnvironment extends Action {
    private final String variable;
    private final Object value;

    public ActionEnvironment(final String message, final String variable, final Object value) {
        super(message);
        this.variable = variable;
        this.value = value;
    }

    @Override
    public void trigger(final Map<String, Object> environment, final Set<Event> events) {
        super.trigger(environment, events);
        environment.put(variable, value);
    }
}
