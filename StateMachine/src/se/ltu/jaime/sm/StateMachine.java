package se.ltu.jaime.sm;

import java.util.*;

public class StateMachine {
	// Would the class Event fit instead of this var?
    private final Set<String> events;
    
    private final Map<String, Object> environment;
    private final List<State> states;
    private final List<Transition> transitions;
    
    // TODO: Enable parallel executions with more than one active state at the same time, ie. List<CurrentStates>
    private int currentState;

    public StateMachine(final List<State> states, final List<Transition> transitions) {
        this(states, transitions, 0);
    }

    public StateMachine(final List<State> states, final List<Transition> transitions, final int currentState) {
        this.events = new HashSet<>();
        this.environment = new HashMap<>();
        this.states = states;
        this.transitions = transitions;
        this.currentState = currentState;
    }

    public State currentState() {
        return states.get(currentState);
    }

    public void event(final String name) {
        events.add(name);
    }

    public void setVariable(final String variable, final Object value) {
        environment.put(variable, value);
    }

    public void update() {
    	final State state = currentState();
        for (final int t: state.transitionsIndexes()) {
            final Transition transition = transitions.get(t);
            if (!transition.guard().evaluate(environment)) {
                continue;
            }
            if (!transition.event().evaluate(events)) {
                continue;
            }
            // If the action always trigger with an environment, is it necessary to have 2 classes one extending the other?
            transition.action().trigger(environment);
            
            currentState = transition.target();
            break; // TODO: Check if any other transitions are also satisfied. If so, throw exception!
        }
        events.clear();

        // TODO: Check if this state is an END state, that would stop the State Machine (and return a boolean value)
    }

    public List<Transition> transitions() {
        return transitions;
    }
}