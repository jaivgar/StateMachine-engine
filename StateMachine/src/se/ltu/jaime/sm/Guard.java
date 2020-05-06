package se.ltu.jaime.sm;

import java.util.Map;
import java.util.Objects;

/**
 * A Guard in the context of a State Machine.
 * <p>
 * A {@code Guard} represents a specific value of a variable that {@link Transition}
 * use as condition to their triggering, and that is match against the environment
 *  of the {@link StateMachine}, being the environment a set of variables with an
 * assigned value.
 * <p>
 * The variable name must be not null, but the value may be null
 * 
 * @see GuardTest#testNullGuardCreationAndEvaluation()
 */
public class Guard implements Evaluable<Map<String, Object>>{
	
	/**
	 * The name of the variable represented by this {@code Guard}, not null
	 */
    private final String variable;
    
    /**
     * The specific value of the variable represented by this {@code Guard}, 
     * may be null
     */
    private final Object value;

    /**
     * Constructs an instance of {@code Guard} with the given variable name and
     * value
     * 
     * @param variable the name of the variable
     * @param value the value of the variable
     * 
     * @throws IllegalArgumentException if the {@link variable} is null
     */
    public Guard(final String variable, final Object value) {
    	
    	checkGuard(variable);
    	
        this.variable = variable;
        this.value = value;
    }
    
    public String getVariable() {
		return variable;
	}

	public Object getValue() {
		return value;
	}

	/**
     * Function that checks if the conditional expression of the guard is true, 
     * which is a requirement to trigger the action of the transition and change state.
     * 
     * @param environment The environment keeps a map of variables that can be 
     * modified in the context of this State Machine.<br> 
     * The variables in the environment are match against the guards of the transitions 
     * and modified by actions or external actors upon the State Machine.
     *
     * @return True if the guard logical expressions matches the environment variable, 
     * false otherwise
     */
    public boolean evaluate(final Map<String, Object> environment) {
    	if (!environment.containsKey(this.getVariable()))
    		return false;
    	
    	return Objects.equals(environment.get(variable), value);

    	/* Is there a difference between above and below?
    	 * Below throws exception when environment.get(variable) == null
    	 */
    	//return environment.get(variable).equals(value);
        
    	
    }
    
    /**
     * Checks the first argument of the object constructor to verify that is not
     * null. 
     * <p>
     * This method was preferred to {@code Objects.requireNonNull()} because it
     * allows to choose the exception thrown, instead of a fixed NullPointerException
     * 
     * @param guardName The name of this guard, used as key in the Map where it 
     * will be stored
     * 
     * @throws IllegalArgumentException if the argument is null
     */
    private void checkGuard(String guardName) {
    	if (guardName == null) {
    		throw new IllegalArgumentException("Guard has no name, so it can not be created");
    	}
	}
}
