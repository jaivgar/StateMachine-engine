package se.ltu.jaime.sm;

public class Transition {
    private final Event event;
    private final Guard guard;
    private final Action action;
    private final int target;

    public Transition(final Event event, final Guard guard, final Action action, final int target) {
        this.event = event;
        this.guard = guard;
        this.action = action;
        this.target = target;
    }

    public Event event() {
        return event;
    }

    public Guard guard() {
        return guard;
    }

    public Action action() {
        return action;
    }

    public int target() {
        return target;
    }
}
