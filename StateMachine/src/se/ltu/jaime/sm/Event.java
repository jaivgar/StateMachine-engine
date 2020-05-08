package se.ltu.jaime.sm;

import java.util.Set;

/**
 * An Event in the context of a State Machine.
 * <p>
 * A {@link StateMachine} contains a list of active {@code Events}, and {@link Transition} 
 * include {@code Events} as part of their conditions to activate and trigger.
 * <p>
 * This class allows the creation of null Event, when the constructor has null
 * as argument.
 * 
 * @see EventTest#testNullEventCreationAndEvaluation()
 *
 */
public class Event implements Evaluable<Set<Event>>{
	
	/**
	 * The name is used to identify this {@code Event}
	 */
    private final String eventName;

    /**
     * Constructs an instance of an {@code Event} with the given name
     * 
     * @param name  The name of the Event, may be null
     */
    public Event(final String name) {
        this.eventName = name;
    }
    
    public String getName() {
		return eventName;
	}
    
	/**
     * Evaluates this event by comparing against the Set of {@code Events}
     * present in the State Machine.
     * <p>
     * Searches in the event set passed as argument if this event has been
     * triggered, comparing their Events name.
     * <p>
     * When the {@code eventName} is null, it will compare against other 
     * {@code Events} that also have a null name, but it will not match against
     * a {@code null} Object
     * 
     * @param names  The set of event names that have been triggered
     * @return True if the event was found, False otherwise
     */
    public boolean evaluate(final Set<Event> names) {

    	for(Event e: names) {
    		// An null object can never match an Event
    		if(e == null) {
    			continue;
    		}
    		// There could be an Event without name, which requires extra test
    		if(e.getName() == null) {
    			if (this.eventName == null) {
    				return true;
    			}
    		}
    		else {
        		if(e.getName().equals(this.eventName)) {
        			return true;
        		}
    		}
    	}
    	
    	return false;
    	/*
    	 * The use of contains() method from Set interface with Event Object 
    	 * requires to override the equals() method from Object class, and 
    	 * therefore also the hash() method
    	 */
        //return names.contains(this);
    }
}
