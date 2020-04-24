package se.ltu.jaime.sm;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LogicOperatorTest {

	Boolean oneTruth;
	Boolean oneFalsity;
	Boolean[] truths;
	Boolean[] falsities;
	
	@BeforeEach
	void setUp() throws Exception {
		oneTruth = true;
		oneFalsity = false;
		truths = new Boolean[]{true, true, true};
		falsities = new Boolean[] {false, false, false};
	}

	@Test
	final void testEvaluateNotOperator() {
		
		assertThrows(IllegalNumberOfOperandsException.class, () -> {
				LogicOperator.NOT.evaluateOperator(truths);
			}
			,"A logic operator NOT should only accept one operand, not more"
		);
		
		try {
			assertTrue(LogicOperator.NOT.evaluateOperator(oneFalsity), 
						"The NOT operator should invert the boolean value from false to true");
		} catch (IllegalNumberOfOperandsException e) {
						e.printStackTrace();
		}
		
		try {
			assertFalse(LogicOperator.NOT.evaluateOperator(oneTruth), 
						"The NOT operator should invert the boolean value from true to false");
		} catch (IllegalNumberOfOperandsException e) {
						e.printStackTrace();
		}
	}

	@Test
	final void testEvaluateAndOperator() {
		
		assertThrows(IllegalNumberOfOperandsException.class, () -> {
				LogicOperator.AND.evaluateOperator(oneTruth);
			}
			,"A logic operator AND should only accept two or more operands, not one"
		);
		
		try {
			assertTrue(LogicOperator.AND.evaluateOperator(truths), 
						"The AND operator should return true only when all the boolean operands are true");
		} catch (IllegalNumberOfOperandsException e) {
						e.printStackTrace();
		}
		
		try {
			assertFalse(LogicOperator.AND.evaluateOperator(oneFalsity,oneFalsity, oneTruth), 
						"The AND operator should return false when any of the boolean operands is false");
		} catch (IllegalNumberOfOperandsException e) {
						e.printStackTrace();
		}
	}
	
	@Test
	final void testEvaluateOrOperator() {
		
		assertThrows(IllegalNumberOfOperandsException.class, () -> {
				LogicOperator.OR.evaluateOperator(oneTruth);
			}
			,"A logic operator OR should only accept two or more operands, not one"
		);
		
		try {
			assertTrue(LogicOperator.OR.evaluateOperator(oneTruth,oneFalsity,oneFalsity), 
						"The OR operator should return true when any of the boolean operands is true");
		} catch (IllegalNumberOfOperandsException e) {
						e.printStackTrace();
		}
		
		try {
			assertFalse(LogicOperator.AND.evaluateOperator(falsities), 
						"The OR operator should return false when all his boolean operands are false");
		} catch (IllegalNumberOfOperandsException e) {
						e.printStackTrace();
		}
	}
	
	@Test
	final void testEvaluateXorOperator() {
		assertThrows(IllegalNumberOfOperandsException.class, () -> {
				LogicOperator.XOR.evaluateOperator(oneTruth);
			}
			,"A logic operator OR should only accept two, not one or more than two"
		);
		
		assertThrows(IllegalNumberOfOperandsException.class, () -> {
				LogicOperator.XOR.evaluateOperator(falsities);
			}
			,"A logic operator OR should only accept two, not one or more than two"
		);
		
		try {
			assertTrue(LogicOperator.XOR.evaluateOperator(oneTruth,oneFalsity), 
						"The XOR operator should return true when the boolean operands are different");
		} catch (IllegalNumberOfOperandsException e) {
						e.printStackTrace();
		}
		
		try {
			assertFalse(LogicOperator.XOR.evaluateOperator(oneTruth, oneTruth), 
						"The XOR operator should return false when all his boolean operands are equal");
		} catch (IllegalNumberOfOperandsException e) {
						e.printStackTrace();
		}
		
		try {
			assertFalse(LogicOperator.XOR.evaluateOperator(oneFalsity, oneFalsity), 
						"The XOR operator should return false when all his boolean operands are equal");
		} catch (IllegalNumberOfOperandsException e) {
						e.printStackTrace();
		}
	}
}
