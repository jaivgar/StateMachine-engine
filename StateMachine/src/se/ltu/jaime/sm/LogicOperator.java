package se.ltu.jaime.sm;

public enum LogicOperator {
	
	/*TODO: I will probably need to wrapp the operator with the event/guards in another object, 
	 * like a EventExpression, because an event can not have an operator (except NOT), they are
	 * relations between operands
	 */
	
	NOT{
		@Override
		public boolean testLogic(final boolean... operands) throws IllegalNumberOfOperandsException {
			// NOT operations can only accept one operand
			if(operands == null || operands.length != 1)
			{
				throw new IllegalNumberOfOperandsException("The operator NOT only accepts as "+
																"input one operand");
			}
			else {
				return !operands[0];
			}
		}
	},
	AND{
		@Override
		public boolean testLogic(final boolean... operands) throws IllegalNumberOfOperandsException {
			// AND operations can only accept two or more operands
			if(operands == null || operands.length < 2)
			{
				throw new IllegalNumberOfOperandsException("The operator AND only accepts as " +
																"input two or more operands");
			}
			else {
				for (int i= 0; i < operands.length; i++) {
					if (operands[i] == false) {
						return false;
					}
				}
				return true;
			}
		}
	},
	OR{
		@Override
		public boolean testLogic(final boolean... operands) throws IllegalNumberOfOperandsException {
			// OR operations can only accept two or more operands
			if(operands == null || operands.length < 2)
			{
				throw new IllegalNumberOfOperandsException("The operator OR only accepts as " + 
																"input two or more operands");
			}
			else {
				for (int i= 0; i < operands.length; i++) {
					if (operands[i] == true) {
						return true;
					}
				}
				return false;
			}
		}
	},
	XOR{
		@Override
		public boolean testLogic(final boolean... operands) throws IllegalNumberOfOperandsException {
			// XOR operations can only accept two operands
			if(operands == null || operands.length != 2)
			{
				throw new IllegalNumberOfOperandsException("The operator XOR only accepts as " + 
																"input two operands");
			}
			else {
				return operands[0] != operands[1];
			}
		}
	};
	
	public abstract boolean testLogic(final boolean... operands) throws IllegalNumberOfOperandsException;

}
