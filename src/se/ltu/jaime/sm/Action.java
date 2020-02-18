package se.ltu.jaime.sm;

import java.util.Map;

public class Action {
    private final String message;

    public Action(final String message) {
        this.message = message;
    }

    public void trigger(final Map<String, Object> environment) {
        System.out.println("ACTION: " + message);
    }
}
