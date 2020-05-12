package se.ltu.jaime.sm;

import java.util.*;

/**
 * A Finite State Machine, following Mealy paradigm.
 * <p>
 * It represent a whole State Machine, as a list of {@link State states} that are 
 * connected through {@link Transition transitions}. The State Machine also has
 * a context, that stores information of the events active and the environment's
 * variables value.
 * <p>
 * The output of the State Machine is present in the actions of the transitions,
 * and the transitions are dependent on the active state and the input (the
 * context).<br>
 * This is in line with a Mealy State Machine behavior, which offers as benefit
 * over Moore State Machines that require less States to model a similar system.
 *  
 * <h4>Implementation notes</h4>
 * This class is immutable
 */
public class StateMachine {
	
	/**
	 * The {@code Set} of {@link Event events} that are active in this State Machine.
	 */
    private final Set<Event> events;
    
    /**
     * The environment of the State Machine, represented by a {@code Map} of
     * variables with a name and a value of any type.
     */
    private final Map<String, Object> environment;
    
    /**
     * The {@code List} of {@link State states} that conform this State Machine.
     */
    private final List<State> states;
    
    /**
     * The {@code List} of {@link Transition transitions} that conform this 
     * State Machine.
     */
    private final List<Transition> transitions;
    
    /* TODO: Enable parallel executions with more than one active state at the 
     * same time, i.e. List<CurrentStates>. But this implies that states have an
     * order, or priority, then the issue is how to order them.
     * If transitions also have priorities, they could impose state order?
     */
    /**
     * The reference of the current/active state in this State Machine
     */
    private int currentState;

    /**
     * Constructs an instance of a State Machine with the given {@code List}
     * of states and transitions, setting the first state as the initial state.
     * 
     * @param states  The list of states of this State Machine
     * @param transitions  The list of transitions part of this State Machine
     */
    public StateMachine(final List<State> states, final List<Transition> transitions) {
        this(states, transitions, 0);
    }

    /**
     * Constructs an instance of a State Machine with the given {@code List}
     * of states and transitions, and the reference to the initial state.
     * <p>
     * It validates the arguments provided, to test that the State Machine is
     * well-formed.
     * 
     * @param states  The list of states of this State Machine
     * @param transitions  The list of transitions part of this State Machine
     * @param currentState  The initial state
     * 
     * @see #checkStateMachine(List, List)
     */
    public StateMachine(final List<State> states, final List<Transition> transitions, final int currentState) {
        
        /* Check consistency of State Machine before creating the object:
         *  - States should not point to Transitions that do not exist
         *  - States should have unique names
         *  - Transitions should not target states that do not exist
         */
        checkStateMachine(states, transitions);
    	
    	this.events = new HashSet<>();
        this.environment = new HashMap<>();
        this.states = states;
        this.transitions = transitions;
        this.currentState = currentState;
        

    }

	/**
     * Obtains the current/active state of the State Machine
     * 
     * @return The State which will be checked for transitions in the {@link #update()} method
     */
    public State getCurrentState() {
        return states.get(currentState);
    }
    
    /**
     * Shows the events queued in the StateMachine, that will be checked upon call of {@link #update()}
     * 
     * @return The Set of Events active in the State Machine
     */
    public Set<Event> getEvents(){
    	return events;
    }
    
    /**
     * Shows the list of variables present in the environment, that will be checked upon call of {@link #update()}
     * 
     * @return The Map of variables used as Environment in the State Machine
     */
    public Map<String, Object> getEnvironment(){
    	return environment;
    }
    
    /**
     * Returns the whole list of transitions, independently if they are attached to any state.
     * 
     * @return The list of Transitions of the State Machine
     */
    public List<Transition> getTransitions() {
        return transitions;
    }

    /**
     * Adds a new event to the event set, kept in the context of this StateMachine.<br>
     * If the event is already present in the set, then the event will not be added.
     * @param name The event name to be added to the set of Events
     */
    public void setEvent(final String name) {
        events.add(new Event(name));
    }
    


    /**
     * Adds a new variable to the State Machine environment, that will be used to test the Transition
     * guards
     * 
     * @param variable The variable name to be saved in the environment
     * @param value The value of the variable, any type of object accepted
     */
    public void setVariable(final String variable, final Object value) {
        environment.put(variable, value);
    }
    
    /**
     * Executes a run of the State Machine, checking the transitions of the current
     * state, performing any actions if the transition is triggered and changing state if there is a
     * target in the active transition.
     * 
     * @return True if the state is not an ending state and the State Machine can keep executing.<br>
     * False if the state is an ending state(has no transitions), and the State Machine has reached a
     * constant state of no change allowed. 
     */
    public boolean update() {
    	final State state = getCurrentState();
    	
    	/* Check if this state is an END state, that would stop the State Machine.
         * Before adding and END state flag to the state object, we can just check if the state has
         * an empty list of transitions.
         */
    	if (state.transitionsIndexes() == null) {
    		return false;
    	}
    	
    	// Go through the transitions of the current state and check if they are triggered.
        for (final int t: state.transitionsIndexes()) {
            final Transition transition = transitions.get(t);
            
            /* If the State Machine is event based, the first and necessary check should be events
             * At the moment, we keep that both are needed, but in the future guards could be optional
             */
            
            //TODO: Here is where we decided if we accept transitions without Events
            /* Now, the behavior is to accept transitions that have no Events, and
             * continue evaluating the  transition, its guards, as if the Events
             * were true 
             */
            if(transition.event() != null) {
                try {
					if (!transition.event().testLogicExpression(events)) {
					    continue;
					}
				} catch (IllegalLogicExpressionException e) {
					// TODO Should I catch the exception or propagate it...
					// Now if the expression is illegal it will be evaluated as false
					e.printStackTrace();
					continue;
				}
            }
            
            //TODO: Here is where we decided if we accept transitions without Guards
            /* Now, the behavior is to accept transitions that have no Guards, and
             * activate the transition, triggering its action, as if the Guards
             * were true
             */
            if(transition.guard() != null) {
            	try {
					if (!transition.guard().testLogicExpression(environment)) {
					    continue;
					}
				} catch (IllegalLogicExpressionException e) {
					// TODO Should I catch the exception or propagate it...
					// Now if the expression is illegal it will be evaluated as false
					e.printStackTrace();
					continue;
				}
            }
            
            /* CLear Events after transition satisfies its conditions (Events & Guards)
             * and before the action is executed
             */
            events.clear();
            
            if(transition.action() != null) {
            	transition.action().trigger(environment, events);
            }
            
            currentState = transition.targetState();
            /* TODO: Check if any other transitions are also satisfied. 
             * If so, remove break, remove event clear before actions and
             * throw exception for nondeterministic behavior?
             */
            break; 
        }
        
        /* The events are removed after they have been checked against all the transitions.
         * But this poses a problem if the transition Action throws an Event, as it would 
         * be immediately removed.
         * Solution: Clear the Event before any action is triggered, but then is not
         * possible to continue testing the transitions. It's fine if we accept only one
         * transition, deterministic State Machine.
         */
        //events.clear();
        return true;
        
    }

    /**
     * Checks that the arguments provided to the State Machine constructor are
     * correct to produce a well-formed State Machine.
     * <p>
     * The tests performed are:
     * <p><ul>
     * <li>Tests that the states contains references to transitions in the 
     * available range
     * <li>Tests the states name for duplicates, each name should be unique in 
     * this State Machine.
     * <li>Tests that the transitions contains references to states in the 
     * available range
     * </ul><p>
     * 
     * @param statesUnderTest  The list of states
     * @param transitionsUnderTest  The list of transitions
     * 
     * @throws IllegalArgumentException if any of the arguments does not follow
     * the rules to create a correct State Machine
     */
    private void checkStateMachine(List<State> statesUnderTest, List<Transition> transitionsUnderTest ) throws IllegalArgumentException {
    	
    	for (final State s: statesUnderTest) {
    		// Check that states do not point to Transitions that do not exist
    		for(int i: s.transitionsIndexes()) {
    			if (i >= transitionsUnderTest.size() || i < 0) {
    				throw new IllegalArgumentException("State points to nonexistent transition");
    			}
    		}
    	}
    	
    	// Check that states do not have duplicate names
    	if(Utility.hasDuplicate(statesUnderTest)){
    		throw new IllegalArgumentException("State Machine contains different states with the "
    											+ "same name");
    	}
    	
    	// Check that transitions should not target states that do not exist
    	for(final Transition t: transitionsUnderTest) {
    		if(t.targetState() >= statesUnderTest.size() || t.targetState() < 0) {
    			throw new IllegalArgumentException("Transition targets nonexistent state");
    		}
    	}
	}
}
