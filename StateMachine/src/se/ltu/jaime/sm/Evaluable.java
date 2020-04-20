package se.ltu.jaime.sm;

/**
 * A class that can be logically evaluated.<br>
 * It can be through the use of logical operators or other mechanisms.
 *
 * @param <T> The class to be evaluated against
 */
public interface Evaluable<T> {

	/**
	 * Evaluates the expression logically.<br>
	 * To evaluate the expression, it needs to have a context to compare and check against.<br>
	 * For example if we say A=1, this might be true in some situations. The context missing is
	 * provided in the arguments, which could be a list of variables like (A=1, B=0, C=3).
	 * 
	 * @param t The object that represents the context to compare.
	 * @return True if the expression is correct, false otherwise.
	 */
	public boolean evaluate(T t);
	
}
