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
	 * test those boolean values with respect to the operator chose.
	 * 
	 * @param context The external context to evaluate each operand
	 * 
	 * @return True if the whole expression is true, after checking operands
	 * and operator.
	 */
	public boolean testLogicExpression(C context) {
		
		List<Boolean> operandsEvaluated = new ArrayList<>();
		for(E e: operands){
			operandsEvaluated.add(e.evaluate(context));
		}
		
		boolean result = false;
		try {
			result = operator.evaluateOperator(operandsEvaluated
												.toArray(new Boolean[operandsEvaluated.size()]));
		} catch (IllegalNumberOfOperandsException e1) {
			// For debugging
			System.out.println("Before exception the Operator was \"" + this.operator 
					+ "\" and the number of operands: " + operandsEvaluated.size());
			e1.printStackTrace();
		}
		return result;
	}
}
