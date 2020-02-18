package se.ltu.jaime.sm;

import java.util.*;

public class StateMachine {
    private final Set<String> events;
    private final Map<String, Object> environment;
    private final List<State> states;
    private final List<Transition> transitions;
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
        final var state = currentState();
        for (final var t: state.transitionsIndexes()) {
            final var transition = transitions.get(t);
            if (!transition.guard().evaluate(environment)) {
                continue;
            }
            if (!transition.event().evaluate(events)) {
                continue;
            }
            transition.action().trigger(environment);
            currentState = transition.target();
            break; // TODO: Check if any other transitions are also satisfied. If so, throw exception!
        }
        events.clear();
    }

    public List<Transition> transitions() {
        return transitions;
    }
}
