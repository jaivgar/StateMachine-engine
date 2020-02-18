package se.ltu.jaime.sm;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(final String[] args) {
        final var machine = new StateMachine(
            Arrays.asList(
                new State("A", 0, 1),
                new State("B", 2),
                new State("C", 3)
            ),
            Arrays.asList(
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
            )
        );

        System.out.println(machine.currentState().name());

        machine.update();
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
    }
}
