package se.ltu.jaime.sm;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EventTest {

	Event eventUnderTest;
	Set<Event> testingSet;
	
	@BeforeEach
	void setUp() throws Exception {
		eventUnderTest = new Event("test");
		testingSet = new HashSet<>();
	}

	@Test
	final void testNullEventCreationAndEvaluation() {
		Event eventNull = new Event(null);

		assertFalse("Events created with null arguments should not throw an exception and "
				+ "when evaluating should look for empty objects, now it should find none "
				+ "because the Set is empty",
					eventNull.evaluate(testingSet));
		
		testingSet.add(null);
		assertFalse("Should not find the null object added to the testing Set",
					eventNull.evaluate(testingSet));
		
		testingSet.clear();
		testingSet.add(eventNull);
		assertTrue("Should find the null Event added to the testing Set", 
					eventNull.evaluate(testingSet));
	}

	@Test
	final void testEvaluate() {
		
		testingSet.add(new Event("fail-test"));
		assertFalse("Should not fint the event, because is not present in the testing Set", 
						eventUnderTest.evaluate(testingSet));
		
		testingSet.add(new Event("test"));
		assertTrue("Should fint the event, because it has been added to the testing Set", 
					eventUnderTest.evaluate(testingSet));

		testingSet.clear();
		testingSet.add(eventUnderTest);
		assertTrue("Should fint the event, because the same one has been added to the testing Set", 
					eventUnderTest.evaluate(testingSet));
	}

}
