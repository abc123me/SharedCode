package net.net16.jeremiahlowe.shared;

public enum ETimeUnit{
	Milliseconds(1), Seconds(1000), Minutes(60000), Hours(3600000), Days(86400000);
	
	public final long milliseconds;
	private ETimeUnit(long ms) {
		milliseconds = ms;
	}
}
