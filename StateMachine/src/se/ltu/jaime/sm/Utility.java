package se.ltu.jaime.sm;

import java.util.HashSet;
import java.util.Set;

/**
 * Includes function that can be of use for the rest of the class, but are not
 * hard coupled to any.
 */
public class Utility {

	/**
	 * Checks weather the collections contains duplicate objects by creating
	 * a {@code Set} of the collection, and testing if all elements can be added.
	 * 
	 * @param <T>  The type of elements stored in the collection
	 * @param all  The collection to be tested for duplicates
	 * @return False if there was no duplicate, true otherwise
	 */
	public static <T> boolean hasDuplicate(Iterable<T> all) {
	    Set<T> set = new HashSet<T>();
	    // Set.add() returns false if the set does not change, which
	    // indicates that a duplicate element has been added.
	    for (T each: all) if (!set.add(each)) return true;
	    return false;
	}
}
