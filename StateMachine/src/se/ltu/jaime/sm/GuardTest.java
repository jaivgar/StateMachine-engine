package se.ltu.jaime.sm;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GuardTest {

	Guard guardUndertest;
	Map<String, Object> testingEnvironment;
	
	@BeforeEach
	void setUp() throws Exception {
		guardUndertest = new Guard("test", 1);
		testingEnvironment = new HashMap<>();
	}

	@Test
	final void testNullGuardCreationAndEvaluation() {
		
		assertThrows(IllegalArgumentException.class, () -> {
				Guard nullGuard = new Guard(null, null);
			}, 
			"Guards created with null arguments should throw an exception, " +
			 "it is required that they provide a name to be index with"
		);
		
		assertDoesNotThrow(() -> {
				Guard nullValueGuard = new Guard("", null);
			}, 
			" Guards created with null value but non null variable name " +
			"should not throw an exception"
		);
		
		Guard nullValueGuard = new Guard("", null);
		
		assertFalse("Should not find a match when the environment is empty",
				nullValueGuard.evaluate(testingEnvironment));
		
		testingEnvironment.put(null, null);
		assertFalse("Should not find the null object added to the testing Environment",
				nullValueGuard.evaluate(testingEnvironment));
		
		testingEnvironment.put("", null);
		assertTrue("Should find the guard when the name matches and value match in the environment",
				nullValueGuard.evaluate(testingEnvironment));
		
	}

	@Test
	final void testEvaluate() {
		testingEnvironment.put("test-fail", 1);
		assertFalse("Should not find the variable, because is not present in the environment",
					guardUndertest.evaluate(testingEnvironment));
		
		testingEnvironment.put("test", 0);
		assertFalse("Should not find the variable, because is has a different value in the environment",
					guardUndertest.evaluate(testingEnvironment));
		
		testingEnvironment.put("test",1);
		assertTrue("Should find the variable, as is present and of equal value in the environment", 
					guardUndertest.evaluate(testingEnvironment));
		
		testingEnvironment.clear();
		testingEnvironment.put(guardUndertest.getVariable(), guardUndertest.getValue());
		assertTrue("Should find the Guard, as the same one has been added in the environment", 
				guardUndertest.evaluate(testingEnvironment));
		
	}

}
