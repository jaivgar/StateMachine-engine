package se.ltu.jaime.sm;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

/**
 * This class wraps together a logic operator and a number of operands
 *
 * @param <E> Type of operand element grouped by this logic expression
 * @param <C> Type of context used to evaluate the operands
 */
public class LogicExpression<E extends Evaluable<C>, C> {

	private final LogicOperator operator;
	private final Collection<E> operands;
	
	public LogicExpression(LogicOperator operator, Collection<E> operands) {
		this.operator = operator;
		this.operands = operands;
	}
	
	/**
	 * Test if the logic expression is true, in a 2 step process.<br>
	 * First checks if each operand is true or false in this context, then
	 * test those boolean values with respect to the operator chosen.
	 * 
	 * @param context The external context to evaluate each operand
	 * 
	 * @return True if the whole expression is true, after checking operands
	 * and operator.
	 * 
	 * @throws IllegalLogicExpressionException if the logic expression is
	 * missing the logic operator and has more than one operand
	 */
	public boolean testLogicExpression(C context) throws IllegalLogicExpressionException {
		
		// No need to check for null, as is previously tested in update() method in StateMachine class
		/*if (operands == null) {
			return true;
		}
		*/
		List<Boolean> operandsEvaluated = new ArrayList<>();
		for(E e: operands){
			operandsEvaluated.add(e.evaluate(context));
		}
		
		boolean result;
		if (operator == null) {
			if (operands.size() != 1) {
				throw new IllegalLogicExpressionException("LogicExpression needs a LogicOperator " + 
															"if more than one operand");
			}
			else {
				result = operandsEvaluated.get(0);
			}
		}
		else {
			try {
				result = operator.evaluateOperator(operandsEvaluated
													.toArray(new Boolean[operandsEvaluated.size()]));
			} catch (IllegalNumberOfOperandsException e1) {
				// For debugging
				System.out.println("Before exception the Operator was \"" + this.operator 
						+ "\" and the number of operands: " + operandsEvaluated.size());
				
				//TODO: Not sure if I should let the program continue or stop it with System.exit(0)
				//TODO Should I catch the exception or propagate it...
				e1.printStackTrace();
				result = false;
			}
		}
		return result;
	}
}
