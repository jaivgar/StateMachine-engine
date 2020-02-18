package se.ltu.jaime.sm;

import java.util.Map;

public class Action {
    private final String message;

    public Action(final String message) {
        this.message = message;
    }

    /**
     * Function that executes code according to the action of the transition
     * 
     * @param environment The environment keeps a map of variables that can be modified outside
     * of this system, and are to be matched to the guards of the transitions
     */
    public void trigger(final Map<String, Object> environment) {
    	
    	// TODO: perform calls to services according to the state or other input
    	
        System.out.println("ACTION: " + message);
    }
}
