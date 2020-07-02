package net.net16.jeremiahlowe.shared.ansi;

public class AnsiStrings {
	public static final String FG = "3", BG = "4";
	
	public static final String FG_BLACK   = FG + 0b000 + "m";
	public static final String FG_RED     = FG + 0b001 + "m";
	public static final String FG_GREEN   = FG + 0b010 + "m";
	public static final String FG_YELLOW  = FG + 0b011 + "m";
	public static final String FG_BLUE    = FG + 0b100 + "m";
	public static final String FG_MAGENTA = FG + 0b101 + "m";
	public static final String FG_CYAN    = FG + 0b110 + "m";
	public static final String FG_WHITE   = FG + 0b111 + "m";
	
	public static final String BG_BLACK   = BG + 0b000 + "m"; 
	public static final String BG_RED     = BG + 0b001 + "m"; 
	public static final String BG_GREEN   = BG + 0b010 + "m"; 
	public static final String BG_YELLOW  = BG + 0b011 + "m"; 
	public static final String BG_BLUE    = BG + 0b100 + "m"; 
	public static final String BG_MAGENTA = BG + 0b101 + "m"; 
	public static final String BG_CYAN    = BG + 0b110 + "m"; 
	public static final String BG_WHITE   = BG + 0b111 + "m"; 
	
	public static final String ESCAPE = "\u001B[";
	public static final String RESET = ESCAPE + "0m";
	public static final String BOLD_MODIFIER = "1;";
	public static final String CLEAR = ESCAPE + "[H" + ESCAPE + "[2J";
}