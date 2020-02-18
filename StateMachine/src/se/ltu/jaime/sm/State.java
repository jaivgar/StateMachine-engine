package se.ltu.jaime.sm;

import java.util.Arrays;
import java.util.List;

public class State {
    private final String name;
    private final List<Integer> transitionsIndexes;

    public State(final String name, final Integer... transitionIndexes) {
        this(name, Arrays.asList(transitionIndexes));
    }

    public State(final String name, final List<Integer> transitionsIndexes) {
        this.name = name;
        this.transitionsIndexes = transitionsIndexes;
    }

    public String name() {
        return name;
    }

    public List<Integer> transitionsIndexes() {
        return transitionsIndexes;
    }
}
