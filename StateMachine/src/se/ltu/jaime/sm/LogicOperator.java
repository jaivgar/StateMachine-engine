package se.ltu.jaime.sm;

/**
 * This enumeration represents the basic logical operators that can be used to test
 * events and guards. 
 * <p>
 * The operators are wrapped with operands in {@link LogicExpression}, that
 * is used by events or guards. Because operators are not isolated entities,
 * they are relations between operands, or modifiers of operands like NOT.
 * <p>
 * At the moment the class supports the operators NOT, AND, OR and XOR.
 * 
 */
public enum LogicOperator {
    
    NOT{
        @Override
        public boolean evaluateOperator(final Boolean... operands) throws IllegalNumberOfOperandsException {
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
        public boolean evaluateOperator(final Boolean... operands) throws IllegalNumberOfOperandsException {
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
        public boolean evaluateOperator(final Boolean... operands) throws IllegalNumberOfOperandsException {
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
        public boolean evaluateOperator(final Boolean... operands) throws IllegalNumberOfOperandsException {
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
    
    /**
     * Combines the boolean representation of the operands according to the operator chosen.
     * 
     * @param operands  The array of operands as boolean variables that will be used by the operators
     */
    public abstract boolean evaluateOperator(final Boolean... operands) throws IllegalNumberOfOperandsException;

}
