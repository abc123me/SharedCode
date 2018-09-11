package net.net16.jeremiahlowe.shared;

public class ArrayUtility {
	@SafeVarargs
	public static <T> boolean anyOf(T any, T... of){
		for(T t : of){
			if(any == t) return true;
		}
		return false;
	}
}
