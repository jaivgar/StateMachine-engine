package se.ltu.jaime.sm;

import java.util.Map;
import java.util.Set;

public class Transition {
    
    // Added support for multiple events and guards with LogicExpression<>
    private final LogicExpression<Event,Set<Event>> events;
    private final LogicExpression<Guard, Map<String, Object>> guards;
    private final Action action;
    private final int targetState;
    
    /*
     *  In the event of several transitions meeting the requirements of being trigger, the use 
     *  of a property as priority will assure deterministic behavior
     */
   

    public Transition(final LogicExpression<Event, Set<Event>> event, final LogicExpression<Guard, Map<String, Object>> guard, final Action action, final int targetState) {
        this.events = event;
        this.guards = guard;
        this.action = action;
        this.targetState = targetState;
    }

    public LogicExpression<Event, Set<Event>> event() {
        return events;
    }

    public LogicExpression<Guard, Map<String, Object>> guard() {
        return guards;
    }

    public Action action() {
        return action;
    }

    public int target() {
        return targetState;
    }
}
