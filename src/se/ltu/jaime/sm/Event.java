package se.ltu.jaime.sm;

import java.util.Set;

public class Event {
    private final Set<String> names;

    public Event(final Set<String> names) {
        this.names = names;
    }

    public boolean evaluate(final Set<String> names) {
        return names.containsAll(this.names);
    }
}
