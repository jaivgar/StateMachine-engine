package se.ltu.jaime.sm;

import java.util.*;

public class StateMachine {
	
    private final Set<Event> events;
    private final Map<String, Object> environment;
    private final List<State> states;
    private final List<Transition> transitions;
    
    // TODO: Enable parallel executions with more than one active state at the same time, ie. List<CurrentStates>
    private int currentState;

    public StateMachine(final List<State> states, final List<Transition> transitions) {
        this(states, transitions, 0);
    }

    public StateMachine(final List<State> states, final List<Transition> transitions, final int currentState) {
        
        /* Check consistency of State Machine before creating the object:
         *  - States should not point to Transitions that do not exist
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
     * Obtains the current state of the State Machine
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
            // Now we proceed analyzing the transition if there is no Events, as if they returned True
            if(transition.event() != null) {
                try {
					if (!transition.event().testLogicExpression(events)) {
					    continue;
					}
				} catch (IllegalLogicExpressionException e) {
					// TODO Should I catch the exception or propagate it...
					e.printStackTrace();
				}
            }
            
            //TODO: Here is where we decided if we accept transitions without Guards
            // Now we proceed analyzing the transition if there is no Guards, as if they returned True
            if(transition.guard() != null) {
            	try {
					if (!transition.guard().testLogicExpression(environment)) {
					    continue;
					}
				} catch (IllegalLogicExpressionException e) {
					// TODO Should I catch the exception or propagate it...
					e.printStackTrace();
				}
            }

            /* If the action always trigger with an environment, is it necessary to have 2 classes 
             * one extending the other?
             */
            if(transition.action() != null) {
            	transition.action().trigger(environment);
            }
            
            currentState = transition.targetState();
            break; // TODO: Check if any other transitions are also satisfied. If so, remove break and throw exception?
        }
        
        // We remove the events after they have been checked against the current state.
        events.clear();
        return true;
        
    }

    private void checkStateMachine(List<State> statesUnderTest, List<Transition> transitionsUnderTest ) throws IllegalArgumentException {
    	// Check that states do not point to Transitions that do not exist
    	for (final State s: statesUnderTest) {
    		for(int i: s.transitionsIndexes()) {
    			if (i >= transitionsUnderTest.size()) {
    				throw new IllegalArgumentException("State points to nonexistent transition");
    			}
    		}
    	}
    	
    	// Check that transitions should not target states that do not exist
    	for(final Transition t: transitionsUnderTest) {
    		if(t.targetState() >= statesUnderTest.size()) {
    			throw new IllegalArgumentException("Transition tarets to nonexistent state");
    		}
    	}
	}
}
