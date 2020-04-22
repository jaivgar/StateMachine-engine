package se.ltu.jaime.sm;

import java.util.Set;

public class Event implements Evaluable<Set<Event>>{
    private final String eventName;

    public Event(final String name) {
        this.eventName = name;
    }

    public String getName() {
		return eventName;
	}
    
	/**
     * Searches if the event set passed as argument has triggered this event.
     * 
     * @param names The set of event names that have been triggered.
     * @return True if the event was found, false otherwise.
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
