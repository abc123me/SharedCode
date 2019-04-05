package net.net16.jeremiahlowe.args;

public class Argument<T> {
	public final String[] names;
	public final int params;

	public Argument(String[] names, int params) {
		if(names.length == 0)
			throw new RuntimeException("Argument has no names!");
		this.names = names;
		this.params = params;
	}
	public Argument(String name, int params) {
		this(new String[] {name}, params);
	}
	
}

