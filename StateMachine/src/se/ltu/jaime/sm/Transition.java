package se.ltu.jaime.sm;

import java.util.Map;
import java.util.Set;

/**
 * A transition on the context of a State Machine.
 * <p>
 * A transition is a set of conditions that when met, trigger some action in the
 * State Machine and some change of state (it can point to the original state too).
 * The conditions are written with {@link LogicExpression logical expressions} and
 * are formed with {@link Event events} or {@link Guard guards}. The actions have
 * freedom to create any output, either to affect the outside world or to change
 * the State Machine internals, by throwing new {@code Events} or changing the
 * value of some {@code Guard}.
 *
 * <h4>Implementation notes</h4>
 * This class is immutable
 */
public class Transition {
    
	/**
	 * The logic expression formed with {@code Events}
	 */
    private final LogicExpression<Event,Set<Event>> events;
    
	/**
	 * The logic expression formed with {@code Guards}
	 */
    private final LogicExpression<Guard, Map<String, Object>> guards;
    
    /**
     * The action triggered by this transition
     */
    private final Action action;
    
    /**
     * The goal state, that will be the current state once the transition has
     * been executed.
     */
    private final int targetState;
    
    /*
     *  In the event of several transitions meeting the requirements of being trigger, the use 
     *  of a property as priority will assure deterministic behavior.
     *  New property?
     */
    
    /**
     * Constructs an instance of a transition with the given logic expressions, 
     * actions and target state.
     * 
     * @param event  The logic expression made of events
     * @param guard  The logic expression made of guards
     * @param action  The action of the transition
     * @param targetState  The state where this transition ends
     */
    public Transition(final LogicExpression<Event, Set<Event>> event, final LogicExpression<Guard, Map<String, Object>> guard, final Action action, final int targetState) {
        this.events = event;
        this.guards = guard;
        this.action = action;
        this.targetState = targetState;
    }

    /**
     * Provides the logic expression formed of {@code Events} attached to this
     * transition
     * 
     * @return The logic expression
     */
    public LogicExpression<Event, Set<Event>> event() {
        return events;
    }

    /**
     * Provides the logic expression formed of {@code Guards} attached to this
     * transition
     * 
     * @return The logic expression
     */
    public LogicExpression<Guard, Map<String, Object>> guard() {
        return guards;
    }

    /**
     * Provides the action executed when this transition is activated
     * 
     * @return The action
     */
    public Action action() {
        return action;
    }

    /**
     * Provides the state that this transition points to, that will be
     * the current state once the transition is activated
     * 
     * @return The index of the target state
     */
    public int targetState() {
        return targetState;
    }
}
