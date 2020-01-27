package net.net16.jeremiahlowe.shared;

import java.io.PrintStream;

public class ArrayUtility {
	@SafeVarargs
	public static <T> boolean anyOf(T any, T... of){
		for(T t : of){
			if(any == t) return true;
		}
		return false;
	}

	public static <T extends Object> void printArray(T[] r) {
		printArray(System.out, r, ", ", System.lineSeparator());
	}
	public static <T extends Object> void printArray(PrintStream to, T[] r, String sep, String end) {
		String out = "";
		for(T p : r)
			out += "\"" + p.toString() + "\"" + sep;
		out = out.substring(0, out.length() - sep.length()) + end;
		to.print(out);
	}
}
