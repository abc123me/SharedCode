package net.net16.jeremiahlowe.shared.ansi;

public class AnsiStyle{
	public static final String ANSI_ESCAPE = "\u001B[";
	
	public static final int FORMAT_ITALIC      = 0x01;
	public static final int FORMAT_UNDERLINE   = 0x02;
	public static final int FORMAT_BRIGHTEN    = 0x04;
	public static final int FORMAT_STRIKE      = 0x08;
	public static final int FORMAT_CONCEAL     = 0x10;
	public static final int FORMAT_DARKEN      = 0x20;
	public static final int FORMAT_SWAP_COLORS = 0x40;
	
	public static final int ANSI_RESET         = 0;
	public static final int ANSI_BRIGHTEN      = 1;
	public static final int ANSI_DARKEN        = 2;
	public static final int ANSI_ITALIC        = 3;
	public static final int ANSI_UNDERLINE     = 4;
	public static final int ANSI_SWAP          = 7;
	public static final int ANSI_CONCEAL       = 8;
	public static final int ANSI_STRIKE        = 9;
	public static final int ANSI_N_BRIGHTEN    = 21;
	public static final int ANSI_N_DEFAULT     = 22;
	public static final int ANSI_N_ITALIC      = 23;
	public static final int ANSI_N_UNDERLINE   = 24;
	public static final int ANSI_N_SWAP        = 27;
	public static final int ANSI_N_CONCEAL     = 28;
	public static final int ANSI_N_STRIKE      = 29;
	
	private static final int N_FORMAT_ITALIC      = ~FORMAT_ITALIC;
	private static final int N_FORMAT_UNDERLINE   = ~FORMAT_UNDERLINE;
	private static final int N_FORMAT_BRIGHTEN    = ~FORMAT_BRIGHTEN;
	private static final int N_FORMAT_DEFAULT     = ~(FORMAT_BRIGHTEN | FORMAT_DARKEN);
	private static final int N_FORMAT_STRIKE      = ~FORMAT_STRIKE;
	private static final int N_FORMAT_CONCEAL     = ~FORMAT_CONCEAL;
	private static final int N_FORMAT_DARKEN      = ~FORMAT_DARKEN;
	private static final int N_FORMAT_SWAP_COLORS = ~FORMAT_SWAP_COLORS;
	
	private int formattingMask;
	
	public AnsiStyle() { this(0); }
	public AnsiStyle(int val) { formattingMask = val; }
	public AnsiStyle(AnsiStyle val) { this(val.formattingMask); }
	
	public void interpretAnsiCode(int code) {
		switch(code) {
			//Normal flags
			case ANSI_RESET:       formattingMask = 0; break;
			case ANSI_BRIGHTEN:    formattingMask |= FORMAT_BRIGHTEN; break;
			case ANSI_DARKEN:      formattingMask |= FORMAT_DARKEN; break;
			case ANSI_ITALIC:      formattingMask |= FORMAT_ITALIC; break;
			case ANSI_UNDERLINE:   formattingMask |= FORMAT_UNDERLINE; break;
			case ANSI_SWAP:        formattingMask |= FORMAT_SWAP_COLORS; break;
			case ANSI_CONCEAL:     formattingMask |= FORMAT_CONCEAL; break;
			case ANSI_STRIKE:      formattingMask |= FORMAT_STRIKE; break;
			// Disable flags
			case ANSI_N_BRIGHTEN:  formattingMask &= N_FORMAT_BRIGHTEN; break;
			case ANSI_N_DEFAULT:   formattingMask &= N_FORMAT_DEFAULT; break;
			case ANSI_N_ITALIC:    formattingMask &= N_FORMAT_ITALIC; break;
			case ANSI_N_UNDERLINE: formattingMask &= N_FORMAT_UNDERLINE; break;
			case ANSI_N_SWAP:      formattingMask &= N_FORMAT_SWAP_COLORS; break;
			case ANSI_N_CONCEAL:   formattingMask &= N_FORMAT_CONCEAL; break;
			case ANSI_N_STRIKE:    formattingMask &= N_FORMAT_STRIKE; break;
			default: break;
		}
	}
	
	public void reset() { formattingMask = 0; }
	
	public boolean italic() {    return (formattingMask & FORMAT_ITALIC)      != 0; }
	public boolean underline() { return (formattingMask & FORMAT_UNDERLINE)   != 0; }
	public boolean brighten() {  return (formattingMask & FORMAT_BRIGHTEN)    != 0; }
	public boolean darken() {    return (formattingMask & FORMAT_DARKEN)      != 0; }
	public boolean conceal() {   return (formattingMask & FORMAT_CONCEAL)     != 0; }
	public boolean strike() {    return (formattingMask & FORMAT_STRIKE)      != 0; }
	public boolean swap() {      return (formattingMask & FORMAT_SWAP_COLORS) != 0; }
	
	
	@Override public String toString() {
		return toString(true);
	}
	public String toString(boolean reset) {
		String out = ANSI_ESCAPE + (reset ? "0;" : "");
		if(italic()) out += "3;";
		if(brighten()) out += "1;";
		if(darken()) out += "2;";
		if(underline()) out += "4;";
		if(strike()) out += "7;";
		return out + "m";
	}
}
