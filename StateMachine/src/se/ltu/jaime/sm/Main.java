package se.ltu.jaime.sm;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import se.ltu.jaime.sm.StateMachine.UpdateAction;


public class Main {
    public static void main(final String[] args) {
    	testWithActions();
    	
    }
    
    public static void testWithActions() {
        final var machine = new StateMachine(
            Arrays.asList(
        		new State("A",0),
        		new State("B", 1),
                new State("C", 2)
            ),
            Arrays.asList(
        		new Transition(
        				null, 
        				null, 
        				(env, events) -> {
        					System.out.println("Transition active: triggers Y event");
        					events.add(new Event("EVENT-Y"));
        					System.out.println("The events are: " + events);
        					System.out.println("The env is: " + env);
        				},
        				1
        		),
        		new Transition(
        				//new LogicExpression<Event,Set<Event>>(null, new HashSet<Event>(Stream.of("EVENT-X").collect(Collectors.toUnmodifiableSet()))),
        				new LogicExpression<Event,Set<Event>>(null, List.of(new Event("EVENT-X"))), 
        				new LogicExpression<Guard,Map<String, Object>>(
        															LogicOperator.NOT, 
																	List.of(new Guard("x", 1))), 
        				(env, events) -> {
        					System.out.println("Transition active: add variable b");
        					env.put("b", 
        							env.get("b") != null ? (int)env.get("b") + 1 : 1);
        					System.out.println("The events are: " + events);
        					System.out.println("The env is: " + env);
        				}, 
        				2
        		),
        		new Transition(
        				//new LogicExpression<Event,Set<Event>>(null, new HashSet<Event>(Stream.of("EVENT-X").collect(Collectors.toUnmodifiableSet()))),
        				new LogicExpression<Event,Set<Event>>(
        									LogicOperator.AND, 
											List.of(new Event("EVENT-X"), new Event("EVENT-Y"))), 
        				new LogicExpression<Guard,Map<String, Object>>(
        									LogicOperator.OR, 
        									List.of(new Guard("x", 4), new Guard("y", 1))), 
        				(env, set) -> {
        					System.out.println("Transition active: Finally both events are present "
        							+ "and x = 4 OR y = 1!!");
        					System.out.println("The events are: " + set);
        					System.out.println("The env is: " + env);
        					
        				},
        				0
        		)
            )
        );

        
        boolean endState = false;
        String stateName;
        
        // Added a bound to limit the amount of runs of cyclic state machines (which never reach endSstate)
        int i = 0;
        
        System.out.println("For initial conditions we set in the Environment X = 0");
        machine.setVariable("x", 0);
        
        // Check every time if we reached the final state to stop operations
        while(!endState && i < 20){
        	stateName = machine.getActiveState().name();
        	System.out.println("\n-> Now in state " + stateName);
        	
        	if (!machine.getActiveState().name().equals("A")) {
        		System.out.println("Received Event X from external actor");
        		machine.setEvent("EVENT-X");
        		
        		if (i%3 == 0) {
            		System.out.println("Received Event Y from external actor");
            		machine.setEvent("EVENT-Y");
        		}
        		else {
        			if ((Integer)machine.getEnvironment().get("x") == 5) {
            			System.out.println("We set variable X = 0, and Y = 1");
            			machine.setVariable("x", 0);
            			machine.setVariable("y", 1);
            		}
            		else {
            			machine.setVariable("x", (int)machine.getEnvironment().get("x") + 1);
            			System.out.println("We increase variable X to " + 
    							(int)machine.getEnvironment().get("x"));
                		if (i%7 == 0) {
                    		System.out.println("We set variable Y = 1");
                    		machine.setVariable("y", 1);
                		}
                		else {
                			machine.setVariable("y", 0);
                			System.out.println("We set Y = 0");
                		}	
            		}
        		}
        		
        	}
        	
        	i++;
        	endState = machine.update().getUpdateAction() == UpdateAction.END;
        }
    }
    
    public static void testNullActions() {
        final var machine = new StateMachine(
            Arrays.asList(
        		new State("A",0),
        		new State("B", 1),
                new State("C", 2)
                
        		/* Old states
                new State("A", 0, 1),
                new State("B", 2),
                new State("C", 3)
                */
            ),
            Arrays.asList(
        		new Transition(
        				null, 
        				null, 
        				null, 
        				1
        		),
        		new Transition(
        				//new LogicExpression<Event,Set<Event>>(null, new HashSet<Event>(Stream.of("EVENT-X").collect(Collectors.toUnmodifiableSet()))),
        				new LogicExpression<Event,Set<Event>>(null, List.of(new Event("EVENT-X"))), 
        				new LogicExpression<Guard,Map<String, Object>>(
        															LogicOperator.NOT, 
																	List.of(new Guard("x", 1))), 
        				null, 
        				2
        		),
        		new Transition(
        				//new LogicExpression<Event,Set<Event>>(null, new HashSet<Event>(Stream.of("EVENT-X").collect(Collectors.toUnmodifiableSet()))),
        				new LogicExpression<Event,Set<Event>>(
        									LogicOperator.AND, 
											List.of(new Event("EVENT-X"), new Event("EVENT-Y"))), 
        				new LogicExpression<Guard,Map<String, Object>>(
        									LogicOperator.OR, 
        									List.of(new Guard("x", 4), new Guard("y", 1))), 
        				null, 
        				0
        		)
        		
        		/* Old transitions
                new Transition(
                    new Event(Collections.emptySet()),
                    new Guard("x", 1),
                    new Action("A --> B!"),
                    1
                ),
                new Transition(
                    new Event(Stream.of("EVENT-X").collect(Collectors.toSet())),
                    new Guard("x", 0),
                    new Action("A --> C!"),
                    2
                ),
                new Transition(
                    new Event(Collections.emptySet()),
                    new Guard("y", "hello"),
                    new Action("B --> A!"),
                    0
                ),
                new Transition(
                    new Event(Collections.emptySet()),
                    new Guard("y", null),
                    new ActionEnvironment("C --> B!", "y", "goodbye"),
                    1
                )
                */
            )
        );

        
        boolean endState = false;
        String stateName;
        
        // Added a bound to limit the amount of runs of cyclic state machines (which never reach endSstate)
        int i = 0;
        
        System.out.println("For initial conditions we set in the Environment X = 0");
        machine.setVariable("x", 0);
        
     // Check every time if we reached the final state to stop operations
        while(!endState && i < 20){
        	stateName = machine.getActiveState().name();
        	System.out.println(stateName);
        	
        	if (!machine.getActiveState().name().equals("A")) {
        		System.out.println("Received Event X from external actor");
        		machine.setEvent("EVENT-X");
        		
        		if (i%3 == 0) {
            		System.out.println("Received Event Y from external actor");
            		machine.setEvent("EVENT-Y");
        		}
        		else {
        			if ((Integer)machine.getEnvironment().get("x") == 5) {
            			System.out.println("We set variable X = 0, and Y = 1");
            			machine.setVariable("x", 0);
            			machine.setVariable("y", 1);
            		}
            		else {
            			machine.setVariable("x", (int)machine.getEnvironment().get("x") + 1);
            			System.out.println("We increase variable X to " + 
    							(int)machine.getEnvironment().get("x"));
                		if (i%7 == 0) {
                    		System.out.println("We set variable Y = 1");
                    		machine.setVariable("y", 1);
                		}
                		else {
                			machine.setVariable("y", 0);
                			System.out.println("We set Y = 0");
                		}	
            		}
        		}
        		
        	}
        	
        	i++;
        	endState = machine.update().getUpdateAction() == UpdateAction.END;
        }
        
        /* Old tests
        System.out.println(machine.currentState().name());

        endState = machine.update();
        if (endState) {
        	System.out.println("We reached the final state!");
        }
        System.out.println(machine.currentState().name());

        machine.event("EVENT-X");
        machine.setVariable("x", 0);
        machine.update();
        System.out.println(machine.currentState().name());

        machine.update();
        System.out.println(machine.currentState().name());

        machine.update();
        System.out.println(machine.currentState().name());

        machine.update();
        System.out.println(machine.currentState().name());

        machine.update();
        System.out.println(machine.currentState().name());
        */
    }
}
