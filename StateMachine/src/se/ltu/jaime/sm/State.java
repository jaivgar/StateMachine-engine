package se.ltu.jaime.sm;

import java.util.Arrays;
import java.util.List;

/**
 * A state of a Finite State Machine.
 * <p>
 * It represents, together with the inputs to the system, the information needed 
 * to know the future outputs of the {@link StateMachine}.
 * <p>
 * A State is identified by a name and has associated with it a {@code List} of
 * {@link Transition transitions}.
 */
public class State {
	
	/**
	 * The name is used to identify this {@code State}
	 */
    private final String name;
    
    /**
     * List of transitions attached to this State
     */
    private final List<Integer> transitionsIndexes;
    
    /**
     * Constructs an instance of an {@code State} with the given name and
     * associated transitions.
     * 
     * @param name  The name used to identify the State, must not be null
     * @param transitionIndexes  The index of the transitions 
     */
    public State(final String name, final Integer... transitionIndexes) {
        this(name, Arrays.asList(transitionIndexes));
    }

    /**
     * Constructs an instance of an {@code State} with the given name and
     * associated list of transitions.
     * 
     * @param name  The name used to identify the State, must not be null
     * @param transitionIndexes  The List of indexes of the transitions 
     */
    public State(final String name, final List<Integer> transitionsIndexes) {
        this.name = name;
        this.transitionsIndexes = transitionsIndexes;
    }

    public String name() {
        return name;
    }

    /**
     * Returns the indexes of the of the Transitions attached to this State
     * 
     * @return The indexes of the Transitions
     */
    public List<Integer> transitionsIndexes() {
        return transitionsIndexes;
    }
}
