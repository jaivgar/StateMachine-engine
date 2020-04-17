package se.ltu.jaime.sm;

import java.util.Map;

public class Action {
    private final String message;

    public Action(final String message) {
        this.message = message;
    }

    /**
     * Function that executes code according to the action attached to the transition
     * 
     * @param environment The environment keeps a map of variables that can be modified in the context
     *  of this State Machine.<br> 
     *  The variables in the environment are match against the guards of the transitions and modified 
     *  by actions or external actors upon the State Machine.
     */
    public void trigger(final Map<String, Object> environment) {
    	
    	// TODO: perform calls to services according to the state or other input
    	
        System.out.println("ACTION: " + message);
    }
}
