package se.ltu.jaime.sm;

import java.util.Set;

public class Event {
    private final Set<String> names;

    public Event(final Set<String> names) {
        this.names = names;
    }

    /**
     * Searches the event set for the specified events.
     * 
     * @param names The event names to be searched for in the event set.
     * @return True if the event was found, false otherwise.
     */
    public boolean evaluate(final Set<String> names) {
        return names.containsAll(this.names);
    }
}
