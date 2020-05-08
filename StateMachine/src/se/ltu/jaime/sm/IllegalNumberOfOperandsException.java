package se.ltu.jaime.sm;

/**
 * An exception thrown when an error occurs during evaluation of an operator.
 * <p>
 * This will be triggered when the number of operands accepted by the operator
 * does not match the number of arguments provided.
 *
 */
@SuppressWarnings("serial")
public class IllegalNumberOfOperandsException extends Exception{
	
    /**
     * Constructs a new exception with the specified message.
     *
     * @param  message  the message to use for this exception, may be null
     */
	public IllegalNumberOfOperandsException(String message) {
		super(message);
	}

}
