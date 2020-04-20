package se.ltu.jaime.sm;

public class Transition {
    
	private final Event event;
    private final Guard guard;
    private final Action action;
    private final int targetState;
    
    /*
     *  In the event of several transitions meeting the requirements of being trigger, the use 
     *  of a property as priority will assure deterministic behavior
     */
   

    public Transition(final Event event, final Guard guard, final Action action, final int targetState) {
        this.event = event;
        this.guard = guard;
        this.action = action;
        this.targetState = targetState;
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
        return targetState;
    }
}
