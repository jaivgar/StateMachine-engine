package se.ltu.jaime.sm;

import java.util.function.BiFunction;

public class FunctionalAction <T, U, R>{

	private BiFunction<T, U, R> action;
	
	public FunctionalAction(BiFunction<T, U, R> action) {
		this.action = action;
	}
	
	public R executeAction(T t, U u){
		
		//Performs the custom action
		return action.apply(t, u);

	}
}
