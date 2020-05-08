package se.ltu.jaime.sm;

import java.util.Map;
import java.util.Set;

/**
 * Represents an Action in a transition, that takes as argument the context
 * of the State Machine:
 * <p><ul>
 * <li>The environment used to test the guards
 * <li>The set of events active at this specific run of the machine
 * </ul><p>
 * <p>This is a {@link java.lang.FunctionalInterface FunctionalInterface} whose 
 * abstract method is {@link #trigger(Map<String, Object>, Set<Event>)}.
 * 
 * @author From Github issue #1 by Emanuel
 *
 */
@FunctionalInterface
public interface Action {
	
	/**
	 * Triggers the action on the State Machine.
	 * <p>
	 * The result of this function depends entirely on the user. It could modify
	 * the environment, activate new Events, print something on the screen or make
	 * calls to an API.
	 * 
	 * @param environment  The Map used to store the variables of the State Machine
	 * @param events  The Set of Events active in the State Machine
	 */
	void trigger(final Map<String, Object> environment, final Set<Event> events);
}
