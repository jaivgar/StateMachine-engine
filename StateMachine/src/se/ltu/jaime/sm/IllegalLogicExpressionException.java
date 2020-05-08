package se.ltu.jaime.sm;

/**
 * An exception thrown when an error occurs due to a malformed Logic Expression.
 * <p>
 * It will be triggered when the operator is missing and there is more than one
 * operand in the expression.
 *
 */
@SuppressWarnings("serial")
public class IllegalLogicExpressionException extends Exception {
	
    /**
     * Constructs a new exception with the specified message.
     *
     * @param  message  the message to use for this exception, may be null
     */
	public IllegalLogicExpressionException(String message) {
		super(message);
	}

}
