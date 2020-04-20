package se.ltu.jaime.sm;

public enum LogicOperator {
	
	/*TODO: I will probably need to wrapp the operator with the event/guards in another object, 
	 * like a EventExpression, because an event can not have an operator (except NOT), they are
	 * relations between operands
	 */
	
	NOT{
		@Override
		public boolean testLogic(final boolean... operands) {
			// NOT operations can only accept one operand
			if(operands == null || operands.length != 1)
			{
				throw new IllegalArgumentException("This operator only accepts as input one operand");
			}
			else {
				return !operands[0];
			}
		}
	},
	AND{
		@Override
		public boolean testLogic(final boolean... operands) {
			// TODO Auto-generated method stub
			return false;
		}
	},
	OR{
		@Override
		public boolean testLogic(final boolean... operands) {
			// TODO Auto-generated method stub
			return false;
		}
	},
	XOR{
		@Override
		public boolean testLogic(final boolean... operands) {
			// TODO Auto-generated method stub
			return false;
		}
	};
	
	public abstract boolean testLogic(final boolean... operands);

}
