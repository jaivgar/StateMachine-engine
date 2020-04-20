package se.ltu.jaime.sm;

import java.util.Map;
import java.util.Objects;

public class Guard implements Evaluable<Map<String, Object>>{
    private final String variable;
    private final Object value;

    public Guard(final String variable, final Object value) {
        this.variable = variable;
        this.value = value;
    }

    /**
     * Function that checks if the conditional expression of the guard is true, which is a requirement
     * to trigger the transition action and change state.
     * 
     * @param environment The environment keeps a map of variables that can be modified in the context
     *  of this State Machine.<br> 
     *  The variables in the environment are match against the guards of the transitions and modified 
     *  by actions or external actors upon the State Machine.
     *
     * @return True if the guard logical expressions matches the environment variable, false otherwise
     */
    public boolean evaluate(final Map<String, Object> environment) {
        return Objects.equals(environment.get(variable), value);
    }
}
